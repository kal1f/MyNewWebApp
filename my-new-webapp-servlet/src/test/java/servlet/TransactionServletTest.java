package servlet;

import binding.request.TransactionRequestBinding;
import binding.request.TransactionUpdateRequestBinding;
import binding.response.*;
import database.entity.Transaction;
import exception.EntityNotFoundException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import service.TransactionService;
import util.DataToJson;
import util.JsonToData;
import util.validator.DataValidator;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TransactionServletTest {
    @Mock
    JsonToData jsonToData;
    @Mock
    DataToJson dataToJson;
    @Mock
    HttpServletRequest request;
    @Mock
    HttpServletResponse response;
    @Mock
    TransactionService transactionService;
    @Mock
    DataValidator dataValidator;
    @Mock
    TransactionUpdateRequestBinding transactionUpdateRequestBinding;
    @Mock
    TransactionRequestBinding transactionRequestBinding;

    TransactionServlet servlet;

    @Before
    public void setUp() throws IOException {

        servlet = new TransactionServlet(dataToJson, jsonToData, transactionService, dataValidator);
        when(jsonToData.jsonToTransactionUpdateData(request)).thenReturn(transactionUpdateRequestBinding);
        when(jsonToData.jsonToTransactionData(request)).thenReturn(transactionRequestBinding);

        when(jsonToData.jsonToTransactionUpdateData(request)).thenReturn(transactionUpdateRequestBinding);
        when(transactionUpdateRequestBinding.getId()).thenReturn(101);
        when(transactionUpdateRequestBinding.getStatus()).thenReturn("CREATED");

        when(dataValidator.isIdValid("2124123")).thenReturn(false);
        when(dataValidator.isIdValid("123131231")).thenReturn(true);
        when(dataValidator.isIdValid("121")).thenReturn(true);


        doNothing().when(dataToJson).processResponse(Matchers.any(HttpServletResponse.class), anyInt(), Matchers.any(ResponseBinding.class));

        when(transactionRequestBinding.getCustomerId()).thenReturn(233);
        when(transactionRequestBinding.getProductId()).thenReturn(123);
        when(transactionRequestBinding.getPaymentType()).thenReturn("CREDITCARD");

    }

    @After
    public void clean(){
        reset(dataToJson, request, response, dataValidator, transactionService);
    }

    @Test
    public void doPutIOExceptionExpectStatus422() throws IOException {

        doThrow(new IOException()).when(jsonToData).jsonToTransactionUpdateData(request);

        servlet.doPut(request, response);

        verify(dataToJson).processResponse(response, 422, ErrorResponseBinding.ERROR_RESPONSE_422);

    }

    @Test
    public void doPutValidInputDataExpectStatus404() throws EntityNotFoundException, IOException {

        when(dataValidator.isTransactionUpdateDataValid(transactionUpdateRequestBinding.getId(),
                transactionUpdateRequestBinding.getStatus())).thenReturn(true);

        doThrow(new EntityNotFoundException()).when(transactionService).updateStatusById(anyString(), Matchers.any(BigInteger.class));

        servlet.doPut(request, response);

        verify(dataToJson).processResponse(response, 404,  ErrorResponseBinding.ERROR_RESPONSE_404);

    }

    @Test
    public void doPutValidDataExpectStatus200() throws EntityNotFoundException, IOException {

        when(transactionUpdateRequestBinding.getId()).thenReturn(90);
        when(dataValidator.isTransactionUpdateDataValid(transactionUpdateRequestBinding.getId(), transactionUpdateRequestBinding.getStatus())).thenReturn(true);

        Transaction t  = new Transaction(122,101,234,new Timestamp(System.currentTimeMillis()), "CREDITCARD","COMPLETE", "123345DS1234");

        when(transactionService.updateStatusById(anyString(), Matchers.any(BigInteger.class))).thenReturn(t);

        servlet.doPut(request, response);

        verify(dataToJson).processResponse(response, 200, new TransactionResponseBinding(t));

    }

    @Test
    public void doPutIdNullDataExpectStatus400() throws IOException {

        when(transactionUpdateRequestBinding.getId()).thenReturn(null);
        when(dataValidator.isTransactionUpdateDataValid(transactionUpdateRequestBinding.getId(),
                transactionUpdateRequestBinding.getStatus())).thenReturn(false);


        servlet.doPut(request, response);

        verify(dataToJson).processResponse(response, 400,
                new ErrorResponseBinding(400, "Input data is not valid"));

    }

    @Test
    public void doGetIdNullExpectStatus200(){

        when(request.getParameter("id")).thenReturn(null);
        List<Transaction> t = new ArrayList<>();

        t.add(new Transaction(132,121, 213,"LOYALTY", "COMPLETE", "DSADS23EDS23"));

        when(transactionService.getTransactions()).thenReturn(t);

        servlet.doGet(request, response);

        verify(dataToJson).processResponse(response, 200, new TransactionsResponseBinding(t) );
    }

    @Test
    public void doGetIdValidAndTransactionNotExistsTransactionWithValidIdExpectStatus404() throws EntityNotFoundException{

        when(request.getParameter("id")).thenReturn("123131231");

        doThrow(new EntityNotFoundException()).when(transactionService).searchTransactionById(Matchers.any(BigInteger.class));

        servlet.doGet(request, response);

        verify(dataToJson).processResponse(response, 404,  ErrorResponseBinding.ERROR_RESPONSE_404);

    }

    @Test
    public void doGetIdValidAndTransactionExistsExpectStatus200() throws EntityNotFoundException{
        when(request.getParameter("id")).thenReturn("121");

        List<Transaction> t = new ArrayList<>();

        t.add(new Transaction(121,1212,13131,"CREDITCARD","COMPLETE", "56789JHGGAHDS2121"));

        when(transactionService.searchTransactionById(BigInteger.valueOf(121))).thenReturn(t);

        servlet.doGet(request, response);

        verify(dataToJson).processResponse(response, 200, new TransactionsResponseBinding(t));

    }

    @Test
    public void doGetIdNotValidExpectStatus400() {
        when(request.getParameter("id")).thenReturn("");

        servlet.doGet(request, response);

        verify(dataToJson, times(1)).processResponse(response, 400,
                new ErrorResponseBinding(400, "Id is not valid"));

    }

    @Test
    public void doPostIOExceptionExpectStatus422() throws IOException {
        doThrow(new IOException()).when(jsonToData).jsonToTransactionData(request);

        servlet.doPost(request, response);

        verify(dataToJson).processResponse(response, 422, ErrorResponseBinding.ERROR_RESPONSE_422);

    }

    @Test
    public void doPostTransactionDataIsValidAndTransactionExistsExpectStatus201() throws EntityNotFoundException{

        when(dataValidator.isTransactionDataValid(transactionRequestBinding.getCustomerId(),
                transactionRequestBinding.getProductId(), transactionRequestBinding.getPaymentType())).thenReturn(true);
        when(transactionRequestBinding.toEntityObject()).thenReturn(new Transaction(101, 121,
                "CREDITCARD","ASF12344DS32435"));
        Transaction transaction = new Transaction();

        transaction.setId(101);
        transaction.setProductId(121);
        transaction.setCustomerId(3123);
        transaction.setPurchTmst(new Timestamp(System.currentTimeMillis()));
        transaction.setPaymentType("CREDITCARD");
        transaction.setStatus("COMPLETE");
        transaction.setCrdCardNumber("23456789087SDF32WE");

        when(transactionService.createTransaction(transactionRequestBinding.toEntityObject())).thenReturn(transaction);

        servlet.doPost(request, response);


        verify(dataToJson).processResponse(response, 201, new TransactionResponseBinding(transaction));
    }

    @Test
    public void doPostTransactionDataIsValidAndTransactionNotExistsExpectStatus404() throws EntityNotFoundException, IOException {

        when(dataValidator.isTransactionDataValid(transactionRequestBinding.getCustomerId(), transactionRequestBinding.getProductId(),
                 transactionRequestBinding.getPaymentType())).thenReturn(true);

        doThrow(new EntityNotFoundException()).when(transactionService).createTransaction(Matchers.any(Transaction.class));

        servlet.doPost(request, response);

        verify(dataToJson).processResponse(response, 404, ErrorResponseBinding.ERROR_RESPONSE_404);
    }

    @Test
    public void doPostTransactionDataIsNotValidThanExpectStatus400() throws IOException {
        when(jsonToData.jsonToTransactionData(request)).thenReturn(transactionRequestBinding);

        when(transactionRequestBinding.getCustomerId()).thenReturn(1232);
        when(transactionRequestBinding.getProductId()).thenReturn(1331);
        when(transactionRequestBinding.getPaymentType()).thenReturn("CREDdaswq1231ARD");

        when(dataValidator.isTransactionDataValid(transactionRequestBinding.getCustomerId(), transactionRequestBinding.getProductId(),
                transactionRequestBinding.getPaymentType())).thenReturn(false);

        servlet.doPost(request, response);

        verify(dataToJson).processResponse(response, 400,new ErrorResponseBinding(400,
                "Transaction can not be registered"));


    }


}