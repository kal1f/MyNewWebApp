package servlet;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import database.CustomerDAO;
import database.entity.Customer;
import org.junit.Test;
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

public class LoginServletTest {


    @Test
    public void whenDoGetThenServletReturnLogPage() throws ServletException, IOException {

        LoginServlet servlet = new LoginServlet();

        final HttpServletRequest request = mock(HttpServletRequest.class);
        final HttpServletResponse response = mock(HttpServletResponse.class);
        final RequestDispatcher dispatcher = mock(RequestDispatcher.class);

        when(request.getRequestDispatcher("login.html")).thenReturn(dispatcher);

        servlet.doGet(request, response);

        verify(request.getRequestDispatcher("login.html"), times(1)).forward(request, response);
        verify(dispatcher).forward(request, response);
    }

    @Test
    public void whenCustomerExistsThenDoPostSetSessionAndReturnPage() throws IOException, ServletException {

        final HttpServletResponse response = mock(HttpServletResponse.class);
        final HttpServletRequest request = mock(HttpServletRequest.class);
        final Customer customer = mock(Customer.class);
        final CustomerDAO cd = mock(CustomerDAO.class);
        final Authentication authentication = mock(Authentication.class);
        final HttpSession session = mock(HttpSession.class);

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
        final HttpServletResponse response = mock(HttpServletResponse.class);
        final HttpServletRequest request = mock(HttpServletRequest.class);
        final Customer customer = mock(Customer.class);
        final CustomerDAO cd = mock(CustomerDAO.class);
        final Authentication authentication = mock(Authentication.class);
        final ResponseHandlerToJson responseHandlerToJson = mock(ResponseHandlerToJson.class);
        final PrintWriter writer  = mock(PrintWriter.class);

        LoginServlet loginServlet = new LoginServlet(cd, authentication, responseHandlerToJson);

        when(request.getParameter("login")).thenReturn("ahmed");
        when(request.getParameter("password1")).thenReturn("1234");


        doCallRealMethod().when(customer).setLogin(anyString());
        doCallRealMethod().when(customer).setPassword(anyString());

        when(cd.isCustomerExist(anyString(),anyString())).thenReturn(false);
        when(response.getWriter()).thenReturn(writer);

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