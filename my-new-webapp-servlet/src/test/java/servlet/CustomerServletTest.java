package servlet;

import binding.request.CustomerSearchRequestBinding;
import binding.response.CustomersResponseBinding;
import binding.response.ErrorResponseBinding;
import binding.response.ResponseBinding;
import database.entity.Customer;
import exception.EntityNotFoundException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import service.CustomerService;
import util.DataToJson;
import util.JsonToData;
import util.validator.DataValidator;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;


import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CustomerServletTest {
    @Mock
    JsonToData jsonToData;
    @Mock
    DataToJson dataToJson;
    @Mock
    HttpServletRequest request;
    @Mock
    HttpServletResponse response;
    @Mock
    CustomerService customerService;
    @Mock
    DataValidator dataValidator;
    @Mock
    CustomerServlet servlet;
    @Mock
    CustomerSearchRequestBinding requestBinding;

    @Before
    public void setUp() throws IOException {

        servlet = new CustomerServlet(customerService, dataToJson, dataValidator, jsonToData);


        when(dataValidator.isWelcomeDataValid(null, null)).thenReturn(false);
        when(dataValidator.isWelcomeDataValid(107, "12asds")).thenReturn(true);
        when(dataValidator.isWelcomeDataValid(101, null)).thenReturn(true);
        when(dataValidator.isWelcomeDataValid(0, "natse12x")).thenReturn(true);
        when(dataValidator.isWelcomeDataValid(121, "qwer12")).thenReturn(true);

        doNothing().when(dataToJson).processResponse(Matchers.any(HttpServletResponse.class), anyInt(), Matchers.any(ResponseBinding.class));

    }

    @After
    public void clean(){
       reset(dataToJson, request, response, customerService, dataValidator);
    }

    @Test
    public void whenIOExceptionExpectStatus422() throws IOException {

        doThrow(new IOException()).when(jsonToData).jsonToWelcomeData(request);

        servlet.doGet(request, response);

        verify(dataToJson).processResponse(response, 422,ErrorResponseBinding.ERROR_RESPONSE_422);

    }

    @Test
    public void whenRequestBindingNullExpectStatus200(){

        ArrayList<Customer> c = new ArrayList<Customer>();

        c.add(new Customer("login", "pass", "name", 1));

        when(customerService.getAllCustomers()).thenReturn(c);

        servlet.doGet(request, response);

        verify(dataToJson).processResponse(response, 200, new CustomersResponseBinding(c) );
    }

    @Test
    public void whenCustomerNotExistsWithParamsExpectStatus404() throws EntityNotFoundException, IOException {

        when(jsonToData.jsonToWelcomeData(request)).thenReturn(requestBinding);

        when(requestBinding.getLogin()).thenReturn("natse12x");
        when(requestBinding.getId()).thenReturn(0);

        doThrow(new EntityNotFoundException()).when(customerService).searchCustomers(Matchers.any(Customer.class));

        servlet.doGet(request, response);

        verify(dataToJson).processResponse(response, 404,  ErrorResponseBinding.ERROR_RESPONSE_404);

    }

    @Test
    public void whenIdNullAndLoginAndCustomerExistsExpectStatus200() throws EntityNotFoundException, IOException {
        when(jsonToData.jsonToWelcomeData(request)).thenReturn(requestBinding);

        when(requestBinding.getLogin()).thenReturn("natse12x");
        when(requestBinding.getId()).thenReturn(0);

        ArrayList<Customer> c = new ArrayList<Customer>();

        c.add(new Customer("login", "pass", "name", 1));

        when(customerService.searchCustomers(requestBinding.toEntityObject())).thenReturn(c);

        servlet.doGet(request, response);

        verify(dataToJson).processResponse(response, 200, new CustomersResponseBinding(c));

    }

    @Test
    public void whenLoginAndIdNullExpectStatus400() throws IOException {
        when(jsonToData.jsonToWelcomeData(request)).thenReturn(requestBinding);

        when(requestBinding.getLogin()).thenReturn(null);
        when(requestBinding.getId()).thenReturn(null);

        servlet.doGet(request, response);

        verify(dataToJson, times(1)).processResponse(response, 400,
                new ErrorResponseBinding(400, "Login or ID is not valid"));

    }

    @Test
    public void whenLoginNullAndIdNotNullReturnJsonWithStatus200() throws EntityNotFoundException, IOException {
        when(jsonToData.jsonToWelcomeData(request)).thenReturn(requestBinding);

        when(requestBinding.getLogin()).thenReturn(null);
        when(requestBinding.getId()).thenReturn(101);

        ArrayList<Customer> c = new ArrayList<Customer>();

        c.add(new Customer("login", "pass", "name", 1));

        when(requestBinding.toEntityObject()).thenReturn(new Customer(101, "login"));
        when(customerService.searchCustomers(requestBinding.toEntityObject())).thenReturn(c);

        servlet.doGet(request, response);

        verify(dataToJson).processResponse(response, 200, new CustomersResponseBinding(c));


    }

    @Test
    public void whenLoginAndIdAndCustomerExistingExpectStatus200() throws EntityNotFoundException, IOException {
        when(jsonToData.jsonToWelcomeData(request)).thenReturn(requestBinding);

        when(requestBinding.getLogin()).thenReturn("qwer12");
        when(requestBinding.getId()).thenReturn(121);

        ArrayList<Customer> c = new ArrayList<Customer>();

        c.add(new Customer("login", "pass", "name", 1));

        when(customerService.searchCustomers(requestBinding.toEntityObject())).thenReturn(c);

        servlet.doGet(request, response);

        verify(dataToJson).processResponse(response, 200, new CustomersResponseBinding(c));
    }


}

