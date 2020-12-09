package servlet;

import binding.request.LoginRequestBinding;
import binding.response.CustomerResponseBinding;
import binding.response.ErrorResponseBinding;
import binding.response.ResponseBinding;
import database.entity.Customer;
import exception.EntityNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import service.LoginService;
import util.DataToJson;
import util.JsonToData;
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
    JsonToData jsonToData;
    @Mock
    DataToJson dataToJson;
    @Mock
    LoginService loginService;
    @Mock
    DataValidator dataValidator;
    @Mock
    LoginRequestBinding requestBinding;

    LoginServlet servlet;


    @Before
    public void setUp() throws ServletException, IOException {

        servlet = new LoginServlet(loginService, dataToJson, dataValidator, jsonToData);

        when(jsonToData.jsonToLoginData(request)).thenReturn(requestBinding);

        when(dataValidator.isLogInDataValid("markR12w","!12*Alex&")).thenReturn(true);
        when(dataValidator.isLogInDataValid("lafw","!12*Alex&")).thenReturn(false);
        when(dataValidator.isLogInDataValid("markR12w","12")).thenReturn(false);
        when(dataValidator.isLogInDataValid(null, null)).thenReturn(false);
        when(request.getSession()).thenReturn(session);

        doNothing().when(dispatcher).forward(request,response);
        doNothing().when(dataToJson).processResponse(Matchers.any(HttpServletResponse.class), anyInt(), Matchers.any(ResponseBinding.class));
    }


    @Test
    public void whenDoPostIOExceptionExpectStatus422() throws IOException {

        doThrow(new IOException()).when(jsonToData).jsonToLoginData(request);

        servlet.doPost(request, response);

        verify(dataToJson).processResponse(response, 422,
                ErrorResponseBinding.ERROR_RESPONSE_422);
    }

    @Test
    public void whenParamsAreValidAndCustomerExistsExpectStatus200() throws EntityNotFoundException {
        //error when delete in setup condition in loginForm

        when(requestBinding.getLogin()).thenReturn("markR12w");
        when(requestBinding.getPassword()).thenReturn("!12*Alex&");

        Customer customer = new Customer(120,"login12", "Alexander","password12!", "1");

        when(loginService.authenticate(anyString(), Matchers.any(Customer.class))).thenReturn(customer);

        servlet.doPost(request, response);

        verify(dataToJson).processResponse(response, 200,
                new CustomerResponseBinding(customer.getId(), customer.getLogin(), customer.getName(), customer.getRole()));
    }

    @Test
    public void whenParamsAreValidCustomerNotExistsThanReturnStatus404() throws EntityNotFoundException {
        //error when delete in setup condition in loginForm

        when(requestBinding.getLogin()).thenReturn("markR12w");
        when(requestBinding.getPassword()).thenReturn("!12*Alex&");

        doThrow(new EntityNotFoundException()).when(loginService).authenticate(anyString(), Matchers.any(Customer.class));

        servlet.doPost(request, response);

        verify(dataToJson).processResponse(response, 404,
                ErrorResponseBinding.ERROR_RESPONSE_404);

    }

    @Test
    public void whenPasswordIsNotValidThenReturnStatus400(){

        when(requestBinding.getLogin()).thenReturn("markR12w");
        when(requestBinding.getPassword()).thenReturn("12");

        servlet.doPost(request, response);

        verify(dataToJson).processResponse(response, 400,new ErrorResponseBinding(400,
                "Login: "+
                        requestBinding.getLogin()+
                        " or password: "+
                        requestBinding.getPassword()+
                        " is not correct")  );
    }

    @Test
    public void whenLoginIsNotValidThenExpectStatus400(){

        when(requestBinding.getLogin()).thenReturn("lafw");
        when(requestBinding.getPassword()).thenReturn("!12*Alex&");

        servlet.doPost(request, response);

        verify(dataToJson).processResponse(response, 400, new ErrorResponseBinding(400,
                "Login: "+
                        requestBinding.getLogin()+
                        " or password: "+
                        requestBinding.getPassword()+
                        " is not correct"));
    }


    @Test
    public void whenLoginAndIdNullThenExpectStatus400(){
        when(requestBinding.getLogin()).thenReturn(null);
        when(requestBinding.getPassword()).thenReturn(null);

        servlet.doPost(request, response);

        verify(dataToJson).processResponse(response, 400, new ErrorResponseBinding(400,
                "Login: "+
                        requestBinding.getLogin()+
                        " or password: "+
                        requestBinding.getPassword()+
                        " is not correct"));
    }

}
