package servlet;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import service.LogoutService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;

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
    LogoutService logoutService;


    @Test
    public void whenCallDoGetThenCustomerAuthenticationIsDeletedAndReturnLoginPage() throws IOException, ServletException {

        LogoutServlet logoutServlet = new LogoutServlet(logoutService);

        doNothing().when(response).setContentType("application/json");
        when(request.getSession()).thenReturn(session);


        when(logoutService.removeCustomerBySessionId(anyString())).thenReturn(0);
        doNothing().when(session).invalidate();
        doNothing().when(response).setStatus(200);
        doNothing().when(response).sendRedirect("/login");

        logoutServlet.doGet(request, response);

        verify(logoutService, times(1)).removeCustomerBySessionId(anyString());
        verify(session, times(1)).invalidate();
        verify(response, times(1)).setStatus(200);
        verify(response, times(1)).sendRedirect("/login");

    }


}
