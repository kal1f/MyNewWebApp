package servlet;

import database.CustomerDAO;
import database.entity.Customer;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import utils.ResponseHandlerToJson;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CustomerServletTest {

    @Mock
    ResponseHandlerToJson responseHandlerToJson;
    @Mock
    CustomerDAO cd;
    @Mock
    HttpServletRequest request;
    @Mock
    HttpServletResponse response;
    @Mock
    ArrayList<Customer> c;


    @Test
    public void whenLoginAndIdEqualsNullThanReturnJson() throws ServletException, IOException {

        CustomerServlet servlet = new CustomerServlet(cd, responseHandlerToJson);

        when(request.getParameter("login")).thenReturn(null);
        when(request.getParameter("id")).thenReturn(null);

        doNothing().when(response).setContentType("application/json");
        doNothing().when(response).setCharacterEncoding("UTF-8");

        when(cd.getCustomers()).thenReturn(c);
        doNothing().when(responseHandlerToJson).processResponse(200, c);

        servlet.doGet(request, response);

        verify(cd, times(1)).getCustomers();
        verify(responseHandlerToJson, times(1)).processResponse(200, c);
    }

    @Test
    public void whenLoginIsNullAndIdIsStringReturnJson() throws ServletException, IOException {

        CustomerServlet servlet = new CustomerServlet(cd, responseHandlerToJson);

        when(request.getParameter("login")).thenReturn(null);
        when(request.getParameter("id")).thenReturn("ahmed");

        doNothing().when(response).setContentType("application/json");
        doNothing().when(response).setCharacterEncoding("UTF-8");

        when(cd.getCustomerByIdOrLogin(request.getParameter("login"), request.getParameter("id"))).thenReturn(c);
        doNothing().when(responseHandlerToJson).processResponse(200, c);

        servlet.doGet(request, response);

        verify(cd, times(1)).getCustomerByIdOrLogin((String) isNull(), anyString());
        verify(responseHandlerToJson, times(1)).processResponse(200,c);

    }

    @Test
    public void whenLoginIsStringAndIdIsNullReturnJson() throws ServletException, IOException{

        CustomerServlet servlet = new CustomerServlet(cd, responseHandlerToJson);

        when(request.getParameter("login")).thenReturn("ahmed");
        when(request.getParameter("id")).thenReturn(null);

        doNothing().when(response).setContentType("application/json");
        doNothing().when(response).setCharacterEncoding("UTF-8");

        when(cd.getCustomerByIdOrLogin(request.getParameter("login"), request.getParameter("id"))).thenReturn(c);
        doNothing().when(responseHandlerToJson).processResponse(200, c);

        servlet.doGet(request, response);

        verify(cd, times(1)).getCustomerByIdOrLogin(anyString(), (String) isNull());
        verify(responseHandlerToJson, times(1)).processResponse(200,c);

    }

    @Test
    public void whenLoginIsNotStringAndIdIsStringReturnJson() throws ServletException, IOException{

        CustomerServlet servlet = new CustomerServlet(cd, responseHandlerToJson);

        when(request.getParameter("login")).thenReturn("ahmed");
        when(request.getParameter("id")).thenReturn("1");

        doNothing().when(response).setContentType("application/json");
        doNothing().when(response).setCharacterEncoding("UTF-8");

        when(cd.getCustomerByIdOrLogin(request.getParameter("login"), request.getParameter("id"))).thenReturn(c);
        doNothing().when(responseHandlerToJson).processResponse(200, c);

        servlet.doGet(request, response);

        verify(cd, times(1)).getCustomerByIdOrLogin(anyString(), anyString());
        verify(responseHandlerToJson, times(1)).processResponse(200,c);

    }

}