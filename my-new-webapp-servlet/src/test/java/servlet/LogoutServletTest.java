package servlet;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import service.LogoutService;
import util.DataToJson;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
    @Mock
    DataToJson dataToJson;

    LogoutServlet logoutServlet;

    @Before
    public void setUp(){
        logoutServlet = new LogoutServlet(logoutService, dataToJson);

        when(request.getSession()).thenReturn(session);
        when(logoutService.unauthenticate(anyString())).thenReturn(0);
        doNothing().when(session).invalidate();
        doNothing().when(dataToJson).processResponse(response, null);
    }

    @Test
    public void whenCallDoGetThenCustomerAuthenticationIsDeletedAndReturnLoginPage() {

        logoutServlet.doPost(request, response);

        verify(logoutService, times(1)).unauthenticate(anyString());
        verify(session, times(1)).invalidate();
        verify(dataToJson, times(1)).processResponse(response, 200,null);

    }


}

