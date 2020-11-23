package servlet;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import service.LogoutService;
import util.HttpResponseModel;
import util.ResponseHandlerToJson;
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
    ResponseHandlerToJson responseHandlerToJson;
    @Mock
    HttpResponseModel httpResponseModel;

    LogoutServlet logoutServlet;

    @Before
    public void setUp(){
        logoutServlet = new LogoutServlet(logoutService, responseHandlerToJson, httpResponseModel);

        when(request.getSession()).thenReturn(session);
        when(logoutService.unauthenticate(anyString())).thenReturn(0);
        doNothing().when(session).invalidate();
        doNothing().when(response).setContentType("application/json");
        doNothing().when(response).setStatus(200);
        doNothing().when(httpResponseModel).setStatus(200);
        doNothing().when(responseHandlerToJson).processResponse(response, httpResponseModel);
    }

    @Test
    public void whenCallDoGetThenCustomerAuthenticationIsDeletedAndReturnLoginPage() {

        logoutServlet.doGet(request, response);

        verify(logoutService, times(1)).unauthenticate(anyString());
        verify(session, times(1)).invalidate();
        verify(response, times(1)).setStatus(200);
       verify(responseHandlerToJson, times(1)).processResponse(response, httpResponseModel);

    }


}

