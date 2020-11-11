package servlet;

import database.entity.Customer;
import org.junit.Test;
import service.Authentication;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class LogoutServletTest {

    @Test
    public void whenCallDoGetThenCustomerAuthenticationIsDeletedAndReturnLoginPage() throws IOException, ServletException {

        final HttpServletResponse response = mock(HttpServletResponse.class);
        final HttpServletRequest request = mock(HttpServletRequest.class);
        final HttpSession session = mock(HttpSession.class);
        final Authentication authentication = mock(Authentication.class);

        final LogoutServlet logoutServlet = new LogoutServlet(authentication);

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