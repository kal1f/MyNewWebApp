package servlet;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import service.RegisterService;
import util.HttpResponseModel;
import util.ResponseHandlerToJson;
import util.validator.DataValidator;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


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
    @Mock
    HttpResponseModel httpResponseModel;

    RegisterServlet servlet;

    @Before
    public void setUp(){
        when(request.getRequestDispatcher("register.html")).thenReturn(dispatcher);

        doNothing().when(httpResponseModel).setStatus(200);
        doNothing().when(httpResponseModel).setStatus(400);
        doNothing().when(httpResponseModel).setMessage("Ok");
        doNothing().when(response).setCharacterEncoding("UTF-8");
        doNothing().when(response).setStatus(200);
        doNothing().when(response).setContentType("application/json");
        when(dataValidator.isRegisterFormValid("markR12w", "Alexander",
                "!12*Alex&", "!12*Alex&")).thenReturn(true);
        when(dataValidator.isRegisterFormValid("markR12w", "Alexander",
                "123", "!123")).thenReturn(false);
        when(registerService.createNewCustomerInDb("markR12w", "Alexander",
                "!12*Alex&")).thenReturn(0);

    }

    @Test
    public void whenCallDoGetThenServletReturnRegisterPage() throws ServletException, IOException {

        servlet = new RegisterServlet();

        servlet.doGet(request, response);

        verify(request, times(1)).getRequestDispatcher("register.html");
        verify(dispatcher).forward(request, response);
    }

    @Test
    public void whenFormIsValidThanSetStatus200() {

        servlet = new RegisterServlet(registerService, responseHandlerToJson,
                dataValidator, httpResponseModel);

        when(request.getParameter("password1")).thenReturn("!12*Alex&");
        when(request.getParameter("password2")).thenReturn("!12*Alex&");
        when(request.getParameter("name")).thenReturn("Alexander");
        when(request.getParameter("login")).thenReturn("markR12w");

        servlet.doPost(request, response);

        verify(registerService, times(1)).createNewCustomerInDb(request.getParameter("login"),
                request.getParameter("name"), request.getParameter("password1"));
        verify(response).setStatus(200);
        verify(response).setContentType("application/json");
        verify(response).setCharacterEncoding("UTF-8");
        verify(httpResponseModel).setStatus(200);
        verify(httpResponseModel).setMessage("Ok");
        verify(responseHandlerToJson).processResponse(response, httpResponseModel);
    }

    @Test
    public void whenFormIsNotValidThanSetStatus400() {

        servlet = new RegisterServlet(registerService, responseHandlerToJson, dataValidator, httpResponseModel);

        servlet.doPost(request, response);

        verify(httpResponseModel).setStatus(400);
        verify(httpResponseModel).setMessage("Customer with this params can't be registered.");
        verify(responseHandlerToJson).processResponse(response, httpResponseModel);


    }

}
