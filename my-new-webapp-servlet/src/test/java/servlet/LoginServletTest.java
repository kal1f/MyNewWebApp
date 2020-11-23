package servlet;

import database.entity.Customer;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import service.LoginService;
import util.HttpResponseModel;
import util.ResponseHandlerToJson;
import util.validator.DataValidator;

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
    @Mock
    HttpResponseModel httpResponseModel;
    @Mock
    DataValidator dataValidator;

    LoginServlet servlet;

    @Before
    public void setUp() throws ServletException, IOException {
        servlet = new LoginServlet(loginService, responseHandlerToJson, httpResponseModel, dataValidator);

        when(request.getSession()).thenReturn(session);
        when(request.getRequestDispatcher("login.html")).thenReturn(dispatcher);
        when(dataValidator.isLogInFormValid("markR12w","!12*Alex&")).thenReturn(true);
        when(dataValidator.isLogInFormValid("lafw","!12*Alex&")).thenReturn(false);
        when(dataValidator.isLogInFormValid("markR12w","12")).thenReturn(false);
        when(request.getSession()).thenReturn(session);

        doNothing().when(dispatcher).forward(request,response);
        doNothing().when(response).setStatus(200);
        doNothing().when(httpResponseModel).setMessage("Ok");
        doNothing().when(httpResponseModel).setStatus(200);
        doNothing().when(httpResponseModel).setStatus(400);
        doNothing().when(httpResponseModel).setStatus(404);
        doNothing().when(httpResponseModel).setMessage("Login: or password: is not valid.");
        doNothing().when(httpResponseModel).setMessage("Ok");
        doNothing().when(httpResponseModel).setError("Unauthorized");
        doNothing().when(responseHandlerToJson).processResponse(response, httpResponseModel);
    }

    @Test
    public void whenDoGetThenServletReturnLoginPage() throws ServletException, IOException {

        servlet = new LoginServlet();
        servlet.doGet(request, response);
        verify(request.getRequestDispatcher("login.html"), times(1)).forward(request, response);
    }

    @Test
    public void whenParamsAreValidAndCustomerExistsThenDoPostReturnWelcomePageAndStatus200(){

        when(request.getParameter("login")).thenReturn("markR12w");
        when(request.getParameter("password1")).thenReturn("!12*Alex&");
        when(loginService.authenticate(anyString(), anyString(), anyString())).thenReturn(new Customer());

        servlet.doPost(request, response);

        verify(response, times(1)).setStatus(200);
        verify(httpResponseModel, times(1)).setStatus(200);
        verify(httpResponseModel, times(1)).setMessage("Ok");
        verify(httpResponseModel, times(1)).setCustomer(loginService.authenticate(anyString(), anyString(), anyString()));
        verify(responseHandlerToJson, times(1)).processResponse(response, httpResponseModel);
    }

    @Test
    public void whenParamsAreValidCustomerNotExistsThanReturnStatus404() throws IOException {

        when(request.getParameter("login")).thenReturn("markR12w");
        when(request.getParameter("password1")).thenReturn("!12*Alex&");


        when(loginService.authenticate(anyString(),anyString(),anyString())).thenReturn(null);

        servlet.doPost(request, response);

        verify(httpResponseModel).setStatus(404);
        verify(httpResponseModel).setError("Unauthorized");
        verify(responseHandlerToJson, times(1)).processResponse(response,httpResponseModel);

    }

    @Test
    public void whenPasswordIsNotValidThenReturnStatus400(){

        LoginServlet loginServlet = new LoginServlet(loginService, responseHandlerToJson,
                httpResponseModel, dataValidator);

        when(request.getParameter("login")).thenReturn("markR12w");
        when(request.getParameter("password1")).thenReturn("12");

        loginServlet.doPost(request, response);

        verify(httpResponseModel).setStatus(400);
        verify(httpResponseModel).setMessage("Login: or password: is not valid.");
        verify(responseHandlerToJson).processResponse(response, httpResponseModel);
    }

    @Test
    public void whenLoginIsNotValidThenReturnStatus400(){

        LoginServlet loginServlet = new LoginServlet(loginService, responseHandlerToJson,
                httpResponseModel, dataValidator);

        when(request.getParameter("login")).thenReturn("lafw");
        when(request.getParameter("password1")).thenReturn("!12*Alex&");

        loginServlet.doPost(request, response);

        verify(httpResponseModel).setStatus(400);
        verify(httpResponseModel).setMessage("Login: or password: is not valid.");
        verify(responseHandlerToJson).processResponse(response, httpResponseModel);
    }


}