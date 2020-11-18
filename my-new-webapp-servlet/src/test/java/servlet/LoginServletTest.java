package servlet;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import service.LoginService;
import util.ResponseHandlerToJson;



import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;

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
    HttpSession session;
    @Mock
    ResponseHandlerToJson responseHandlerToJson;
    @Mock
    LoginService loginService;


    @Test
    public void whenDoGetThenServletReturnLogPage() throws ServletException, IOException {

        LoginServlet servlet = new LoginServlet();


        when(request.getRequestDispatcher("login.html")).thenReturn(dispatcher);

        servlet.doGet(request, response);

        verify(request.getRequestDispatcher("login.html"), times(1)).forward(request, response);
        verify(dispatcher).forward(request, response);
    }

    @Test
    public void whenParamsAreValidAndCustomerExistsThenDoPostReturnWelcomePage() throws IOException {

        LoginServlet loginServlet = new LoginServlet(loginService, responseHandlerToJson);

        when(request.getParameter("login")).thenReturn("markR12w");
        when(request.getParameter("password1")).thenReturn("!12*Alex&");


        when(loginService.returnExistedUserInJson(anyString(),anyString(),anyString())).thenReturn("json");

        when(request.getSession()).thenReturn(session);
        doNothing().when(response).setStatus(200);
        doNothing().when(response).sendRedirect("/welcome");

        loginServlet.doPost(request, response);

        verify(response, times(1)).setStatus(200);
        verify(response, times(1)).sendRedirect("/welcome");

    }

    @Test
    public void whenParamsAreValidCustomerNotExistsThanDoPostReturnJsonPage() throws IOException {

        LoginServlet loginServlet = new LoginServlet(loginService, responseHandlerToJson);

        when(request.getParameter("login")).thenReturn("markR12w");
        when(request.getParameter("password1")).thenReturn("!12*Alex&");


        when(loginService.returnExistedUserInJson(anyString(),anyString(),anyString())).thenReturn(null);

        when(request.getSession()).thenReturn(session);
        doNothing().when(responseHandlerToJson).processResponse(response,404 , null);

        loginServlet.doPost(request, response);

        verify(responseHandlerToJson, times(1)).processResponse(response,404, null);

    }

    @Test
    public void whenOneOfParamsIsNotValidThenReturnLoginPage() throws IOException {

        LoginServlet loginServlet = new LoginServlet(loginService, responseHandlerToJson);

        when(request.getParameter("login")).thenReturn("12");
        when(request.getParameter("password1")).thenReturn("!12*Alex&");

        loginServlet.doPost(request, response);

        verify(response).sendRedirect("/login");
    }


}
