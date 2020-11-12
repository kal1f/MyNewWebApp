package servlet;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import database.CustomerDAO;
import database.entity.Customer;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import service.Authentication;
import service.AuthenticationImpl;
import utils.ResponseHandlerToJson;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class LoginServletTest {
    @Mock
    HttpServletResponse response;
    @Mock
    HttpServletRequest request;
    @Mock
    RequestDispatcher dispatcher;
    @Mock
    Customer customer;
    @Mock
    CustomerDAO cd;
    @Mock
    Authentication authentication;
    @Mock
    HttpSession session;
    @Mock
    ResponseHandlerToJson responseHandlerToJson;

    @Test
    public void whenDoGetThenServletReturnLogPage() throws ServletException, IOException {

        LoginServlet servlet = new LoginServlet();


        when(request.getRequestDispatcher("login.html")).thenReturn(dispatcher);

        servlet.doGet(request, response);

        verify(request.getRequestDispatcher("login.html"), times(1)).forward(request, response);
        verify(dispatcher).forward(request, response);
    }

    @Test
    public void whenCustomerExistsThenDoPostSetSessionAndReturnPage() throws IOException, ServletException {

        LoginServlet loginServlet = new LoginServlet(cd, authentication);

        when(request.getParameter("login")).thenReturn("ahmed");
        when(request.getParameter("password1")).thenReturn("1234");


        doCallRealMethod().when(customer).setLogin(anyString());
        doCallRealMethod().when(customer).setPassword(anyString());


        when(cd.isCustomerExist(anyString(),anyString())).thenReturn(true);
        when(request.getSession()).thenReturn(session);

        doNothing().when(authentication).setCustomer(anyString(), eq(customer));

        doNothing().when(response).setStatus(200);
        doNothing().when(response).sendRedirect("/welcome");

        loginServlet.doPost(request, response);

        verify(cd, times(1)).isCustomerExist(anyString(), anyString());
        verify(response, times(1)).setStatus(200);
        verify(response, times(1)).sendRedirect("/welcome");

    }

    @Test
    public void whenCustomerNotExistsThanDoPostReturnJsonPage() throws ServletException, IOException {

        LoginServlet loginServlet = new LoginServlet(cd, authentication, responseHandlerToJson);

        when(request.getParameter("login")).thenReturn("ahmed");
        when(request.getParameter("password1")).thenReturn("1234");


        doCallRealMethod().when(customer).setLogin(anyString());
        doCallRealMethod().when(customer).setPassword(anyString());

        when(cd.isCustomerExist(anyString(),anyString())).thenReturn(false);
        //when(response.getWriter()).thenReturn(writer);

        doNothing().when(response).setCharacterEncoding("UTF-8");
        doNothing().when(response).setContentType("application/json");
        doNothing().when(response).setHeader("cache-control", "no-cache");

        doNothing().when(responseHandlerToJson).processResponse(404 , null);

        loginServlet.doPost(request, response);

        verify(cd, times(1)).isCustomerExist(anyString(), anyString());
        verify(response, times(1)).setContentType("application/json");
        verify(responseHandlerToJson, times(1)).processResponse(404, null);

    }

}