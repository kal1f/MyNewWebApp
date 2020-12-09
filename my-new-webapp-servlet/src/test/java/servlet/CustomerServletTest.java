package servlet;

import binding.request.CustomerRequestBinding;
import binding.request.CustomerUpdateRequestBinding;
import binding.response.CustomerResponseBinding;
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
import org.mockito.Mockito;
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
    CustomerUpdateRequestBinding requestBinding;

    CustomerServlet servlet;

    @Before
    public void setUp() {

        servlet = new CustomerServlet(customerService, dataToJson, dataValidator, jsonToData);


        when(dataValidator.isWelcomeDataValid(null, null)).thenReturn(false);
        when(dataValidator.isWelcomeDataValid("107", "12asds")).thenReturn(true);
        when(dataValidator.isWelcomeDataValid("101", "")).thenReturn(true);
        when(dataValidator.isWelcomeDataValid("", "natse12x")).thenReturn(true);
        when(dataValidator.isWelcomeDataValid("121", "qwer12")).thenReturn(true);

        doNothing().when(dataToJson).processResponse(Matchers.any(HttpServletResponse.class), anyInt(), Matchers.any(ResponseBinding.class));

    }

    @After
    public void clean(){
       reset(dataToJson, request, response, customerService, dataValidator);
    }

    @Test
    public void doPutIOExceptionExpectStatus422() throws IOException {

        doThrow(new IOException()).when(jsonToData).jsonToCustomerUpdateData(request);

        servlet.doPut(request, response);

        verify(dataToJson).processResponse(response, 422,ErrorResponseBinding.ERROR_RESPONSE_422);

    }

    @Test
    public void doPutIdValidCustomerValidNotExistsExpectStatus404() throws EntityNotFoundException, IOException {

        when(jsonToData.jsonToCustomerUpdateData(request)).thenReturn(requestBinding);

        when(requestBinding.getCustomer()).thenReturn(Mockito.mock(CustomerRequestBinding.class));
        when(requestBinding.getId()).thenReturn(90);
        when(requestBinding.getCustomer().getLogin()).thenReturn("login");
        when(requestBinding.getCustomer().getName()).thenReturn("name");
        when(requestBinding.getCustomer().getPassword1()).thenReturn("pass1");
        when(requestBinding.getCustomer().getPassword2()).thenReturn("pass2");
        when(dataValidator.isCustomerUpdateDataValid(requestBinding.getId(), requestBinding.getCustomer().getLogin(),
                requestBinding.getCustomer().getName(), requestBinding.getCustomer().getPassword1(),
                requestBinding.getCustomer().getPassword2())).thenReturn(true);

        doThrow(new EntityNotFoundException()).when(customerService).updateCustomer(Matchers.any(Customer.class), anyInt());

        servlet.doPut(request, response);

        verify(dataToJson).processResponse(response, 404,  ErrorResponseBinding.ERROR_RESPONSE_404);

    }

    @Test
    public void doPutValidDataValidExpectStatus200() throws EntityNotFoundException, IOException {
        when(requestBinding.getCustomer()).thenReturn(Mockito.mock(CustomerRequestBinding.class));
        when(jsonToData.jsonToCustomerUpdateData(request)).thenReturn(requestBinding);
        when(requestBinding.getId()).thenReturn(101);
        when(requestBinding.getCustomer().getLogin()).thenReturn("login");
        when(requestBinding.getCustomer().getName()).thenReturn("name");
        when(requestBinding.getCustomer().getPassword1()).thenReturn("pass1");
        when(requestBinding.getCustomer().getPassword2()).thenReturn("pass2");
        when(dataValidator.isCustomerUpdateDataValid(requestBinding.getId(), requestBinding.getCustomer().getLogin(),
                requestBinding.getCustomer().getName(), requestBinding.getCustomer().getPassword1(),
                requestBinding.getCustomer().getPassword2())).thenReturn(true);

        Customer c  = new Customer(101,"login", "natse12x", "name", "1");

        when(customerService.updateCustomer(Matchers.any(Customer.class), anyInt())).thenReturn(c);

        servlet.doPut(request, response);

        verify(dataToJson).processResponse(response, 200, new CustomerResponseBinding(c));

    }

    @Test
    public void doPutIdNullDataExpectStatus400() throws IOException {
        when(jsonToData.jsonToCustomerUpdateData(request)).thenReturn(requestBinding);
        when(requestBinding.getCustomer()).thenReturn(Mockito.mock(CustomerRequestBinding.class));
        when(requestBinding.getId()).thenReturn(null);
        when(requestBinding.getCustomer().getLogin()).thenReturn("login");
        when(requestBinding.getCustomer().getName()).thenReturn("name");
        when(requestBinding.getCustomer().getPassword1()).thenReturn("pass1");
        when(requestBinding.getCustomer().getPassword2()).thenReturn("pass2");
        when(dataValidator.isCustomerUpdateDataValid(requestBinding.getId(), requestBinding.getCustomer().getLogin(),
                requestBinding.getCustomer().getName(), requestBinding.getCustomer().getPassword1(),
                requestBinding.getCustomer().getPassword2())).thenReturn(false);


        servlet.doPut(request, response);

        verify(dataToJson).processResponse(response, 400,
                new ErrorResponseBinding(400, "Input data in not valid"));

    }

    @Test
    public void doGetIdNullLoginNullExpectStatus200(){

        when(request.getParameter("login")).thenReturn(null);
        when(request.getParameter("id")).thenReturn(null);
        ArrayList<Customer> c = new ArrayList<Customer>();

        c.add(new Customer("login", "pass", "name", 1));

        when(customerService.getAllCustomers()).thenReturn(c);

        servlet.doGet(request, response);

        verify(dataToJson).processResponse(response, 200, new CustomersResponseBinding(c) );
    }

    @Test
    public void doGetCustomerNotExistsWithParamsExpectStatus404() throws EntityNotFoundException{

        when(request.getParameter("login")).thenReturn("natse12x");
        when(request.getParameter("id")).thenReturn("");

        doThrow(new EntityNotFoundException()).when(customerService).searchCustomers(Matchers.any(Customer.class));

        servlet.doGet(request, response);

        verify(dataToJson).processResponse(response, 404,  ErrorResponseBinding.ERROR_RESPONSE_404);

    }

    @Test
    public void doGetIdValidLoginValidAndCustomerExistsExpectStatus200() throws EntityNotFoundException{
        when(request.getParameter("login")).thenReturn("natse12x");
        when(request.getParameter("id")).thenReturn("");

        ArrayList<Customer> c = new ArrayList<>();

        c.add(new Customer(101,"login", "natse12x", "name", "1"));

        when(customerService.searchCustomers(new Customer(0,
                "natse12x"))).thenReturn(c);

        servlet.doGet(request, response);

        verify(dataToJson).processResponse(response, 200, new CustomersResponseBinding(c));

    }

    @Test
    public void doGetLoginAndIdNullExpectStatus400() {
        when(request.getParameter("login")).thenReturn("");
        when(request.getParameter("id")).thenReturn("");

        servlet.doGet(request, response);

        verify(dataToJson, times(1)).processResponse(response, 400,
                new ErrorResponseBinding(400, "Login or ID is not valid"));

    }

    @Test
    public void doGetLoginNullAndIdNotNullExpect200() throws EntityNotFoundException{

        when(request.getParameter("login")).thenReturn("");
        when(request.getParameter("id")).thenReturn("101");

        ArrayList<Customer> c = new ArrayList<Customer>();

        c.add(new Customer(101,"login", "pass", "name", "1"));

        when(customerService.searchCustomers(new Customer(101,""))).thenReturn(c);

        servlet.doGet(request, response);

        verify(dataToJson).processResponse(response, 200, new CustomersResponseBinding(c));
    }

    @Test
    public void doGetLoginAndIdAndCustomerExistingExpectStatus200() throws EntityNotFoundException{
        when(request.getParameter("login")).thenReturn("qwer12");
        when(request.getParameter("id")).thenReturn("121");

        ArrayList<Customer> c = new ArrayList<Customer>();

        c.add(new Customer("login", "pass", "name", 1));

        when(customerService.searchCustomers(new Customer(121,"qwer12"))).thenReturn(c);

        servlet.doGet(request, response);

        verify(dataToJson).processResponse(response, 200, new CustomersResponseBinding(c));
    }


}

