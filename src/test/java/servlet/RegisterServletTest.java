package servlet;

import database.CustomerDAO;
import database.CustomerDAOImpl;
import database.entity.Customer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import service.Authentication;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class RegisterServletTest {

    @Mock
    Customer c;
    @Mock
    CustomerDAO cd;
    @Mock
    HttpServletRequest request;
    @Mock
    HttpServletResponse response;
    @Mock
    RequestDispatcher dispatcher;

    @Test
    public void whenCallDoGetThenServletReturnRegisterPage() throws ServletException, IOException {

        RegisterServlet servlet = new RegisterServlet();

        when(request.getRequestDispatcher("register.html")).thenReturn(dispatcher); //var... args => OngoingStubbing<T> thenReturn(T value, T... values);

        servlet.doGet(request, response);

        verify(request, times(1)).getRequestDispatcher("register.html");
        verify(dispatcher).forward(request, response);
    }

    @Test
    public void whenCallDoPostNewCustomerCreatingAndReturnLoginPage() throws ServletException, IOException {

        RegisterServlet servlet = new RegisterServlet();

        when(request.getParameter("password1")).thenReturn("1");
        when(request.getParameter("name")).thenReturn("1");
        when(request.getParameter("login")).thenReturn("1");

        doCallRealMethod().when(c).setLogin("1");
        doCallRealMethod().when(c).setPassword("1");
        doCallRealMethod().when(c).setName("1");

        when(cd.insertCustomer(c)).thenReturn(anyInt());

        cd.insertCustomer(c);

        doNothing().when(response).setCharacterEncoding("UTF-8");
        doNothing().when(response).setStatus(201);
        doNothing().when(response).setContentType("application/json");
        doNothing().when(response).sendRedirect("login.html");

        servlet.init();
        servlet.doPost(request, response);


        verify(cd, times(1)).insertCustomer(c);
        verify(request, times(1)).getParameter("name");
        verify(response).setStatus(201);
        verify(response).sendRedirect("login.html" );

        assertEquals(anyInt(),cd.insertCustomer(c));

    }

}