package servlet;

import binding.request.CustomerRegisterRequestBinding;
import binding.response.CustomerResponseBinding;
import binding.response.ErrorResponseBinding;
import database.entity.Customer;
import exception.CustomerNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import service.RegisterService;
import util.DataToJson;
import util.JsonToData;
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
    DataToJson dataToJson;
    @Mock
    JsonToData jsonToData;
    @Mock
    CustomerRegisterRequestBinding requestBinding;

    RegisterServlet servlet;

    @Before
    public void setUp() throws IOException {
        when(jsonToData.jsonToRegisterData(request)).thenReturn(requestBinding);
        when(request.getRequestDispatcher("register.html")).thenReturn(dispatcher);
        when(dataValidator.isRegisterFormValid("markR12w", "Alexander",
                "!12*Alex&", "!12*Alex&")).thenReturn(true);
        when(dataValidator.isRegisterFormValid("markR12w", "Alexander",
                "123", "!123")).thenReturn(false);

    }

    @Test
    public void whenDoGetExpectRegisterPage() throws ServletException, IOException {

        servlet = new RegisterServlet();

        servlet.doGet(request, response);

        verify(request, times(1)).getRequestDispatcher("register.html");
        verify(dispatcher).forward(request, response);
    }

    @Test
    public void whenDoGetServletExceptionExpectStatus500() throws ServletException, IOException {
        servlet = new RegisterServlet(dataToJson);

        doThrow(new ServletException()).when(dispatcher).forward(request, response);

        servlet.doGet(request, response);

        verify(dataToJson).processResponse(response, 500, ErrorResponseBinding.ERROR_RESPONSE_500);
    }

    @Test
    public void whenDoGetIOExceptionExpectStatus500() throws ServletException, IOException {
        servlet = new RegisterServlet(dataToJson);
        doThrow(new IOException()).when(dispatcher).forward(request, response);


        servlet.doGet(request, response);

        verify(dataToJson).processResponse(response, 500,ErrorResponseBinding.ERROR_RESPONSE_500);
    }

    @Test
    public void whenDoPostIOExceptionExpectStatus422() throws IOException {
        servlet = new RegisterServlet(registerService, dataToJson, dataValidator, jsonToData);

        doThrow(new IOException()).when(jsonToData).jsonToRegisterData(request);

        servlet.doPost(request, response);

        verify(dataToJson).processResponse(response, 422, ErrorResponseBinding.ERROR_RESPONSE_422);

    }

    @Test
    public void whenFormIsValidAndCustomerExistsExpectStatus201() throws CustomerNotFoundException {

        servlet = new RegisterServlet(registerService, dataToJson, dataValidator, jsonToData);

        Customer customer = new Customer();

        customer.setId(101);
        customer.setName("Alex");
        customer.setLogin("aslex2");

        when(requestBinding.getPassword1()).thenReturn("!12*Alex&");
        when(requestBinding.getPassword2()).thenReturn("!12*Alex&");
        when(requestBinding.getName()).thenReturn("Alexander");
        when(requestBinding.getLogin()).thenReturn("markR12w");

        when(registerService.createNewCustomerInDb(requestBinding.toCustomer())).thenReturn(customer);

        servlet.doPost(request, response);


        verify(dataToJson).processResponse(response, 201, new CustomerResponseBinding(customer));
    }

    @Test
    public void whenFormIsValidAndCustomerNotExistsExpectStatus404() throws CustomerNotFoundException{

        servlet = new RegisterServlet(registerService, dataToJson, dataValidator, jsonToData);

        when(requestBinding.getPassword1()).thenReturn("!12*Alex&");
        when(requestBinding.getPassword2()).thenReturn("!12*Alex&");
        when(requestBinding.getName()).thenReturn("Alexander");
        when(requestBinding.getLogin()).thenReturn("markR12w");

        doThrow(new CustomerNotFoundException()).when(registerService).createNewCustomerInDb(Matchers.any(Customer.class));

        servlet.doPost(request, response);

        verify(dataToJson).processResponse(response, 404, ErrorResponseBinding.ERROR_RESPONSE_404);
    }

    @Test
    public void whenFormIsNotValidThanSeExpectStatus400() {

        servlet = new RegisterServlet(registerService, dataToJson, dataValidator, jsonToData);

        when(requestBinding.getPassword1()).thenReturn("123");
        when(requestBinding.getPassword2()).thenReturn("!123");
        when(requestBinding.getName()).thenReturn("Alexander");
        when(requestBinding.getLogin()).thenReturn("markR12w");

        servlet.doPost(request, response);

        verify(dataToJson).processResponse(response, 400,new ErrorResponseBinding(400,
                "Customer with login:"+requestBinding.getLogin()+
                        " name: "+requestBinding.getName()+
                        " password1: "+requestBinding.getPassword1()+
                        " password2: "+requestBinding.getPassword2()+
                        " can not be registered"));


    }

}

