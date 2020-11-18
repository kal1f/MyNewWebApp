package servlet;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import service.RegisterService;

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

    @Test
    public void whenCallDoGetThenServletReturnRegisterPage() throws ServletException, IOException {

        RegisterServlet servlet = new RegisterServlet();

        when(request.getRequestDispatcher("register.html")).thenReturn(dispatcher); //var... args => OngoingStubbing<T> thenReturn(T value, T... values);

        servlet.doGet(request, response);

        verify(request, times(1)).getRequestDispatcher("register.html");
        verify(dispatcher).forward(request, response);
    }

    @Test
    public void whenCallDoPostNewCustomerCreatingAndReturnLoginPage() throws IOException {

        RegisterServlet servlet = new RegisterServlet(registerService);

        when(request.getParameter("password1")).thenReturn("1");
        when(request.getParameter("name")).thenReturn("1");
        when(request.getParameter("login")).thenReturn("1");

        when(registerService.createNewCustomerInDb(anyString(), anyString(), anyString())).thenReturn(0);

        doNothing().when(response).setCharacterEncoding("UTF-8");
        doNothing().when(response).setStatus(201);
        doNothing().when(response).setContentType("application/json");
        doNothing().when(response).sendRedirect("/login");

        servlet.doPost(request, response);


        verify(registerService, times(1)).createNewCustomerInDb(anyString(), anyString(), anyString());
        verify(request, times(1)).getParameter("name");
        verify(response).setStatus(201);
        verify(response).setContentType("application/json");
        verify(response).sendRedirect("/login" );


    }

}
