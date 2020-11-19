package servlet;


import database.entity.Customer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import service.CustomerService;
import util.ResponseHandlerToJson;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;

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
    ArrayList<Customer> c;


    @Test
    public void returnCustomersJson() throws IOException {
        CustomerServlet servlet = new CustomerServlet(customerService, responseHandlerToJson);

        when(request.getParameter("login")).thenReturn(null);
        when(request.getParameter("id")).thenReturn(null);

        doNothing().when(responseHandlerToJson).processResponse(response, 200, c);

        servlet.doGet(request, response);

        verify(responseHandlerToJson, times(1)).processResponse(response,200, customerService.returnCustomers(anyInt(), anyString()));
    }

    @Test
    public void whenLoginAndIdEqualsNullThanReturnAllCustomers() throws IOException {

        CustomerServlet servlet = new CustomerServlet(customerService, responseHandlerToJson);

        when(request.getParameter("login")).thenReturn(null);
        when(request.getParameter("id")).thenReturn(null);

        ArrayList<Customer> c = customerService.returnCustomers(null, request.getParameter("login"));

        doNothing().when(responseHandlerToJson).processResponse(response, 200, c);
        servlet.doGet(request, response);
        verify(responseHandlerToJson, times(1)).processResponse(response, 200, customerService.returnCustomers(null, null));
    }

    @Test
    public void whenLoginNullAndIdNotNullReturnCustomersWithIdEquals() throws IOException {

        CustomerServlet servlet = new CustomerServlet( customerService, responseHandlerToJson);

        when(request.getParameter("login")).thenReturn(null);
        when(request.getParameter("id")).thenReturn("101");

        ArrayList<Customer> c = customerService.returnCustomers(1, request.getParameter("login"));

        doNothing().when(responseHandlerToJson).processResponse(response, 200, c);
        servlet.doGet(request, response);
        verify(responseHandlerToJson, times(1)).processResponse(response, 200, customerService.returnCustomers(1, null));

    }

    @Test
    public void whenIdNullAndLoginNotNullReturnCustomersWithLoginEquals() throws IOException{

        CustomerServlet servlet = new CustomerServlet(customerService, responseHandlerToJson);

        when(request.getParameter("login")).thenReturn("natse12x");
        when(request.getParameter("id")).thenReturn(null);

        ArrayList<Customer> c = customerService.returnCustomers(0, request.getParameter("login"));

        doNothing().when(responseHandlerToJson).processResponse(response, 200, c);
        servlet.doGet(request, response);
        verify(responseHandlerToJson, times(1)).processResponse(response, 200, customerService.returnCustomers(0, "ahmed"));


    }

    @Test
    public void whenLoginAndIdNotNullReturnsCustomersWhomDataMatch() throws IOException{

        CustomerServlet servlet = new CustomerServlet(customerService, responseHandlerToJson);

        when(request.getParameter("login")).thenReturn("qwer12");
        when(request.getParameter("id")).thenReturn("121");

        ArrayList<Customer> c = customerService.returnCustomers(Integer.parseInt(request.getParameter("id")), request.getParameter("login"));

        doNothing().when(responseHandlerToJson).processResponse(response, 200, c);
        servlet.doGet(request, response);
        verify(responseHandlerToJson, times(1)).processResponse(response, 200, customerService.returnCustomers(1, "ahmed"));

    }

}