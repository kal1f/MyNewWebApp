package servlet;

import database.entity.Customer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import service.Authentication;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class LogoutServletTest {
    @Mock
    HttpSession session;
    @Mock
    HttpServletRequest request;
    @Mock
    HttpServletResponse response;
    @Mock
    Authentication authentication;


    @Test
    public void whenCallDoGetThenCustomerAuthenticationIsDeletedAndReturnLoginPage() throws IOException, ServletException {

        LogoutServlet logoutServlet = new LogoutServlet(authentication);

        doNothing().when(response).setContentType("application/json");
        when(request.getSession()).thenReturn(session);


        doNothing().when(authentication).removeCustomer(anyString());
        doNothing().when(session).invalidate();
        doNothing().when(response).setStatus(200);
        doNothing().when(response).sendRedirect("/login");

        //logoutServlet.init();
        logoutServlet.doGet(request, response);

        verify(authentication, times(1)).removeCustomer(anyString());
        verify(session, times(1)).invalidate();
        verify(response, times(1)).setStatus(200);
        verify(response, times(1)).sendRedirect("/login");

    }


}