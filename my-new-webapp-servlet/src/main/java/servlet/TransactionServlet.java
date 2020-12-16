package servlet;

import binding.request.TransactionRequestBinding;
import binding.request.TransactionUpdateRequestBinding;
import binding.response.*;
import database.entity.Transaction;
import exception.EntityNotFoundException;
import service.TransactionService;
import service.impl.TransactionServiceImpl;
import util.DataToJson;
import util.JsonToData;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import util.validator.DataValidator;
import java.io.IOException;
import java.math.BigInteger;
import java.util.List;


public class TransactionServlet extends HttpServlet {
    private DataToJson dataToJson;
    private JsonToData jsonToData;
    private TransactionService transactionService;
    private DataValidator dataValidator;

    static final Logger LOGGER = Logger.getLogger(TransactionServlet.class);

    public TransactionServlet() {
        super();
    }

    public TransactionServlet(DataToJson dataToJson, JsonToData jsonToData,
                              TransactionService transactionService, DataValidator dataValidator) {
        this.dataToJson = dataToJson;
        this.jsonToData = jsonToData;
        this.transactionService = transactionService;
        this.dataValidator = dataValidator;
    }

    @Override
    public void init(){
        this.dataToJson = new DataToJson();
        this.jsonToData = new JsonToData();
        this.dataValidator = new DataValidator();
        this.transactionService = new TransactionServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response){
        String id = request.getParameter("id");

        if(id == null){
            List<Transaction> transactions = transactionService.getTransactions();
            dataToJson.processResponse(response,  200, new TransactionsResponseBinding(transactions));
        }
        else if(dataValidator.isIdValid(id)){
            try {
                List<Transaction> transactions = transactionService.searchTransactionById(new BigInteger(id));
                dataToJson.processResponse(response, 200, new TransactionsResponseBinding(transactions));
            } catch (EntityNotFoundException e) {
                LOGGER.debug("id is not existing", e);
                dataToJson.processResponse(response, 404, ErrorResponseBinding.ERROR_RESPONSE_404);
            }
        }
        else {
            LOGGER.debug("Id is not valid");
            dataToJson.processResponse(response, 400,
                    new ErrorResponseBinding(400, "Id is not valid"));
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response){
        TransactionRequestBinding requestBinding;

        try {
            requestBinding = jsonToData.jsonToTransactionData(request);
        }catch (IOException e){
            LOGGER.debug(e.getMessage(), e);
            dataToJson.processResponse(response, 422, ErrorResponseBinding.ERROR_RESPONSE_422);
            return;
        }
        if(dataValidator.isTransactionDataValid(requestBinding.getCustomerId(), requestBinding.getProductId(), requestBinding.getPaymentType())) {
            try {
                Transaction transaction = transactionService.createTransaction(requestBinding.toEntityObject());
                LOGGER.debug("Transaction is created");
                dataToJson.processResponse(response, 201, new TransactionResponseBinding(transaction));
            } catch (EntityNotFoundException e) {
                LOGGER.debug("Transaction is not created", e);
                dataToJson.processResponse(response, 404, ErrorResponseBinding.ERROR_RESPONSE_404);
            }
        }
        else {
            LOGGER.info("Transaction can not be registered");

            dataToJson.processResponse(response, 400,
                    new ErrorResponseBinding(400,"Transaction can not be registered"));
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response){
        TransactionUpdateRequestBinding requestBinding = null;

        try{
            requestBinding=jsonToData.jsonToTransactionUpdateData(request);
        }catch (IOException e){
            LOGGER.debug(e.getMessage(), e);
            dataToJson.processResponse(response, 422, ErrorResponseBinding.ERROR_RESPONSE_422);
            return;
        }

        if(dataValidator.isTransactionUpdateDataValid(requestBinding.getId(), requestBinding.getStatus())){
            try {
                Transaction t = transactionService.updateStatusById(requestBinding.getStatus(), BigInteger.valueOf(requestBinding.getId()));
                dataToJson.processResponse(response, 200, new TransactionResponseBinding(t));
            }catch (EntityNotFoundException e){
                LOGGER.debug("Product with id"+
                        requestBinding.getId()+
                        "was not been updated" , e);
                dataToJson.processResponse(response, 404, ErrorResponseBinding.ERROR_RESPONSE_404);
            }
        }
        else {
            LOGGER.debug("Input data is not valid");

            dataToJson.processResponse(response, 400, new ErrorResponseBinding(400,
                    "Input data is not valid"));
        }
    }
}
