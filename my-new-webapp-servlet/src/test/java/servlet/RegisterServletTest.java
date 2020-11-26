/*
package servlet;

import binding.request.CustomerRegisterRequestBinding;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import service.RegisterService;
import util.DataToJson;
import util.validator.DataValidator;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

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
    DataToJson dataToJson;
    @Mock
    CustomerRegisterRequestBinding requestBinding;

    RegisterServlet servlet;

    @Before
    public void setUp(){
        when(requestBinding.toCustomer()).thenReturn();

        when(request.getRequestDispatcher("register.html")).thenReturn(dispatcher);
        when(dataValidator.isRegisterFormValid("markR12w", "Alexander",
                "!12*Alex&", "!12*Alex&")).thenReturn(true);
        when(dataValidator.isRegisterFormValid("markR12w", "Alexander",
                "123", "!123")).thenReturn(false);
        when(registerService.createNewCustomerInDb(requestBinding.toCustomer())).thenReturn(0);
    }

    @Test
    public void whenDoGetThenServletReturnRegisterPage() throws ServletException, IOException {

        servlet = new RegisterServlet();

        servlet.doGet(request, response);

        verify(request, times(1)).getRequestDispatcher("register.html");
        verify(dispatcher).forward(request, response);
    }

    @Test
    public void whenDoGetServletExceptionResponseStatus500() throws ServletException, IOException {
        servlet = new RegisterServlet(dataToJson);

        doThrow(ServletException.class).when(dispatcher).forward(request, response);

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        servlet.doGet(request, response);

        verify(response).setStatus(500);
        verify(dataToJson).processResponse(response, null);
    }

    @Test
    public void whenDoGetIOExceptionResponseStatus500() throws ServletException, IOException {
        servlet = new RegisterServlet(dataToJson);
        doThrow(new IOException()).when(dispatcher).forward(request, response);

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        servlet.doGet(request, response);

        verify(response).setStatus(500);
        verify(dataToJson).processResponse(response, null);
    }

    @Test
    public void whenFormIsValidThanSetStatus200() {

        servlet = new RegisterServlet(registerService, dataToJson,
                dataValidator);

        when(request.getParameter("password1")).thenReturn("!12*Alex&");
        when(request.getParameter("password2")).thenReturn("!12*Alex&");
        when(request.getParameter("name")).thenReturn("Alexander");
        when(request.getParameter("login")).thenReturn("markR12w");

        servlet.doPost(request, response);

        verify(registerService, times(1)).createNewCustomerInDb(request.getParameter("login"),
                request.getParameter("name"), request.getParameter("password1"));
        verify(response).setStatus(200);
        verify(dataToJson).processResponse(response, null);
    }

    @Test
    public void whenFormIsNotValidThanSetStatus400() {

        servlet = new RegisterServlet(registerService, dataToJson, dataValidator);

        servlet.doPost(request, response);

        verify(response).setStatus(400);
        verify(dataToJson).processResponse(response, null);


    }

}
*/

