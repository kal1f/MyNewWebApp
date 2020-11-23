package servlet;

import database.entity.Customer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import service.CustomerService;
import util.HttpResponseModel;
import util.ResponseHandlerToJson;
import util.validator.DataValidator;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.ArrayList;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CustomerServletTest {

    @Mock
    ResponseHandlerToJson responseHandlerToJson;
    @Mock
    HttpServletRequest request;
    @Mock
    HttpServletResponse response;
    @Mock
    CustomerService customerService;
    @Mock
    DataValidator dataValidator;
    @Mock
    HttpResponseModel httpResponseModel;

    CustomerServlet servlet;

    @Before
    public void setUp(){
        servlet = new CustomerServlet(customerService, responseHandlerToJson, httpResponseModel, dataValidator);

        when(dataValidator.isWelcomeFormValid(null, null)).thenReturn(false);
        when(dataValidator.isWelcomeFormValid("101", null)).thenReturn(true);
        when(dataValidator.isWelcomeFormValid(null, "natse12x")).thenReturn(true);
        when(dataValidator.isWelcomeFormValid("121", "qwer12")).thenReturn(true);
        doNothing().when(responseHandlerToJson).processResponse(response, httpResponseModel);

        doNothing().when(httpResponseModel).setMessage("Ok");
        doNothing().when(httpResponseModel).setStatus(200);
        doNothing().when(httpResponseModel).setStatus(400);
        doNothing().when(httpResponseModel).setMessage("Login or id is not valid");
    }

    @After
    public void clean(){
       reset(responseHandlerToJson, request, response, customerService, dataValidator, httpResponseModel);
    }

    @Test
    public void whenLoginAndIdEqualsNullReturnJsonStatus400() {
        when(request.getParameter("login")).thenReturn(null);
        when(request.getParameter("id")).thenReturn(null);

        servlet.doGet(request, response);

        verify(httpResponseModel).setStatus(400);
        verify(httpResponseModel).setMessage("Login or id is not valid");
        verify(responseHandlerToJson, times(1)).processResponse(response, null);
    }

    @Test
    public void whenLoginNullAndIdNotNullReturnJsonWithStatus200() {

        when(request.getParameter("login")).thenReturn(null);
        when(request.getParameter("id")).thenReturn("101");

        ArrayList<Customer> c = customerService.searchCustomers(servlet.convertStringToInteger(request.getParameter("id")),
                request.getParameter("login"));

        servlet.doGet(request, response);

        verify(httpResponseModel).setStatus(200);
        verify(httpResponseModel).setMessage("Ok");
        verify(httpResponseModel).setCustomers(c);
        verify(responseHandlerToJson, times(1)).processResponse(response, httpResponseModel);


    }

    @Test
    public void whenIdNullAndLoginNotNullReturnJsonWithStatus200(){

        CustomerServlet servlet = new CustomerServlet(customerService, responseHandlerToJson ,httpResponseModel, dataValidator);

        when(request.getParameter("login")).thenReturn("natse12x");
        when(request.getParameter("id")).thenReturn(null);

        ArrayList<Customer> c = customerService.searchCustomers(servlet.convertStringToInteger(request.getParameter("id")),
                request.getParameter("login"));

        servlet.doGet(request, response);

        verify(httpResponseModel).setStatus(200);
        verify(httpResponseModel).setMessage("Ok");
        verify(httpResponseModel).setCustomers(c);
        verify(responseHandlerToJson, times(1)).processResponse(response, httpResponseModel);

    }

    @Test
    public void whenLoginAndIdNotNullReturnJsonWithStatus200(){

        CustomerServlet servlet = new CustomerServlet(customerService, responseHandlerToJson, httpResponseModel, dataValidator);

        when(request.getParameter("login")).thenReturn("qwer12");
        when(request.getParameter("id")).thenReturn("121");

        ArrayList<Customer> c = customerService.searchCustomers(servlet.convertStringToInteger(request.getParameter("id")),
                request.getParameter("login"));

        servlet.doGet(request, response);

        verify(httpResponseModel).setStatus(200);
        verify(httpResponseModel).setMessage("Ok");
        verify(httpResponseModel).setCustomers(c);
        verify(responseHandlerToJson, times(1)).processResponse(response, httpResponseModel);
    }

}
