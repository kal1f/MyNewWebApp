package servlet;

import binding.request.CustomerWelcomeRequestBinding;
import binding.response.CustomerWelcomeResponseBinding;
import binding.response.ErrorResponseBinding;
import binding.response.ResponseBinding;
import database.entity.Customer;
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
    CustomerWelcomeRequestBinding requestBinding;

    @Before
    public void setUp() throws IOException {

        servlet = new CustomerServlet(customerService, dataToJson, dataValidator, jsonToData);

        when(jsonToData.jsonToWelcomeData(request)).thenReturn(requestBinding);
        when(dataValidator.isWelcomeFormValid("0", null)).thenReturn(false);
        when(dataValidator.isWelcomeFormValid("101", null)).thenReturn(true);
        when(dataValidator.isWelcomeFormValid("0", "natse12x")).thenReturn(true);
        when(dataValidator.isWelcomeFormValid("121", "qwer12")).thenReturn(true);

        doNothing().when(dataToJson).processResponse(Matchers.any(HttpServletResponse.class), anyInt(), Matchers.any(ResponseBinding.class));

    }

    @After
    public void clean(){
       reset(dataToJson, request, response, customerService, dataValidator);
    }

    @Test
    public void whenServletExceptionExpectStatus500() throws IOException {
        doThrow(new IOException()).when(jsonToData).jsonToWelcomeData(request);

        servlet.doPost(request, response);

        verify(dataToJson).processResponse(response, 500,ErrorResponseBinding.ERROR_RESPONSE_500);

    }
    @Test
    public void whenLoginOrIdNotExistingExpectStatus404(){
        when(requestBinding.getLogin()).thenReturn("natse12x");
        when(requestBinding.getId()).thenReturn(0);

        when(customerService.searchCustomers(requestBinding.toCustomer())).thenReturn(new ArrayList<>());
        servlet.doPost(request, response);

        verify(dataToJson).processResponse(response, 404,new ErrorResponseBinding(404,
                "Customers with login:"+ requestBinding.getLogin()+
                        " id: "+ requestBinding.getId()+
                        " are not existing"));

    }

    @Test
    public void whenLoginAndIdNullExpectStatus400() {
        when(requestBinding.getLogin()).thenReturn(null);
        when(requestBinding.getId()).thenReturn(0);

        servlet.doPost(request, response);

        verify(dataToJson, times(1)).processResponse(response, 400, new ErrorResponseBinding(400,
                "Login: "+ requestBinding.getLogin()+
                        " or id: "+ requestBinding.getId()+
                        " is not valid"));

    }

    @Test
    public void whenLoginNullAndIdNotNullReturnJsonWithStatus200() {

        when(requestBinding.getLogin()).thenReturn(null);
        when(requestBinding.getId()).thenReturn(101);

        ArrayList<Customer> c = new ArrayList<Customer>();

        c.add(new Customer("login", "pass", "name", 1));

        when(requestBinding.toCustomer()).thenReturn(new Customer(101, "login"));
        when(customerService.searchCustomers(requestBinding.toCustomer())).thenReturn(c);


        servlet.doPost(request, response);

        verify(dataToJson).processResponse(response, 200, new CustomerWelcomeResponseBinding(c));


    }

    @Test
    public void whenIdNullAndLoginAndCustomerExistingExpectStatus200(){

        when(requestBinding.getLogin()).thenReturn("natse12x");
        when(requestBinding.getId()).thenReturn(0);

        ArrayList<Customer> c = new ArrayList<Customer>();

        c.add(new Customer("login", "pass", "name", 1));

        when(customerService.searchCustomers(requestBinding.toCustomer())).thenReturn(c);

        servlet.doPost(request, response);

        verify(dataToJson).processResponse(response, 200, new CustomerWelcomeResponseBinding(c));

    }

    @Test
    public void whenLoginAndIdAndCustomerExistingExpectStatus200(){

        when(requestBinding.getLogin()).thenReturn("qwer12");
        when(requestBinding.getId()).thenReturn(121);

        ArrayList<Customer> c = new ArrayList<Customer>();

        c.add(new Customer("login", "pass", "name", 1));

        when(customerService.searchCustomers(requestBinding.toCustomer())).thenReturn(c);

        servlet.doPost(request, response);

        verify(dataToJson).processResponse(response, 200, new CustomerWelcomeResponseBinding(c));
    }

    @Test
    public void whenDoGetExpectStatus200(){

        ArrayList<Customer> c = new ArrayList<Customer>();

        c.add(new Customer("login", "pass", "name", 1));

        when(customerService.outAllCustomers()).thenReturn(c);

        servlet.doGet(request, response);

        verify(dataToJson).processResponse(response, 200, new CustomerWelcomeResponseBinding(c));
    }
}

