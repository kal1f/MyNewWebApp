package servlet;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import service.RegisterService;
import util.ResponseHandlerToJson;
import util.validator.DataValidator;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.Response;

import java.io.IOException;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class RegisterServletTest {

    @Mock
    HttpServletRequest request;
    @Mock
    HttpServletResponse response;
    @Mock
    RequestDispatcher dispatcher;
    @Mock
    RegisterService registerService;
    @Mock
    DataValidator dataValidator;
    @Mock
    ResponseHandlerToJson responseHandlerToJson;

    @Test
    public void whenCallDoGetThenServletReturnRegisterPage() throws ServletException, IOException {

        RegisterServlet servlet = new RegisterServlet();

        when(request.getRequestDispatcher("register.html")).thenReturn(dispatcher); //var... args => OngoingStubbing<T> thenReturn(T value, T... values);

        servlet.doGet(request, response);

        verify(request, times(1)).getRequestDispatcher("register.html");
        verify(dispatcher).forward(request, response);
    }

    @Test
    public void whenAllParamsAreValidThanNewCustomerCreatingAndReturnLoginPage() throws IOException {

        RegisterServlet servlet = new RegisterServlet(registerService, dataValidator, responseHandlerToJson);

        when(request.getParameter("password1")).thenReturn("!12*Alex&");
        when(request.getParameter("password2")).thenReturn("!12*Alex&");
        when(request.getParameter("name")).thenReturn("Alexander");
        when(request.getParameter("login")).thenReturn("markR12w");

        when(dataValidator.isRegisterFormValid("markR12w", "Alexander","!12*Alex&", "!12*Alex&")).thenReturn(true);

        when(registerService.createNewCustomerInDb("markR12w", "Alexander", "!12*Alex&")).thenReturn(0);

        doNothing().when(response).setCharacterEncoding("UTF-8");
        doNothing().when(response).setStatus(201);
        doNothing().when(response).setContentType("application/json");
        doNothing().when(response).sendRedirect("/login");

        servlet.doPost(request, response);


        verify(registerService, times(1)).createNewCustomerInDb("markR12w", "Alexander", "!12*Alex&");
        verify(request, times(1)).getParameter("name");
        verify(response).setStatus(201);
        verify(response).setContentType("application/json");
        verify(response).sendRedirect("/login" );
    }

    @Test
    public void whenOneOfParamsAreNotValidThanReturnStatus400() throws IOException {

        RegisterServlet servlet = new RegisterServlet(registerService, dataValidator, responseHandlerToJson);

        when(request.getParameter("password1")).thenReturn("123");
        when(request.getParameter("password2")).thenReturn("123");
        when(request.getParameter("name")).thenReturn("Alexander");
        when(request.getParameter("login")).thenReturn("markR12w");

        when(dataValidator.isRegisterFormValid("markR12w", "Alexander","123", "!123")).thenReturn(false);

        servlet.doPost(request, response);

        verify(responseHandlerToJson, times(1)).processResponse(response, 400, null);


    }

}
