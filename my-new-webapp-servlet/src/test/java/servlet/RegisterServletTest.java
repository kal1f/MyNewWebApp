package servlet;

import binding.request.CustomerRequestBinding;
import binding.response.CustomerResponseBinding;
import binding.response.ErrorResponseBinding;
import database.entity.Customer;
import database.entity.Role;
import exception.EntityNotFoundException;
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
    RegisterService registerService;
    @Mock
    DataValidator dataValidator;
    @Mock
    DataToJson dataToJson;
    @Mock
    JsonToData jsonToData;
    @Mock
    CustomerRequestBinding requestBinding;

    RegisterServlet servlet;

    @Before
    public void setUp() throws IOException {
        servlet = new RegisterServlet(registerService, dataToJson, dataValidator, jsonToData);

        when(jsonToData.jsonToRegisterData(request)).thenReturn(requestBinding);
        when(dataValidator.isCustomerDataValid("markR12w", "Alexander",
                "!12*Alex&", "!12*Alex&")).thenReturn(true);
        when(dataValidator.isCustomerDataValid("markR12w", "Alexander",
                "123", "!123")).thenReturn(false);

    }


    @Test
    public void whenDoPostIOExceptionExpectStatus422() throws IOException {

        doThrow(new IOException()).when(jsonToData).jsonToRegisterData(request);

        servlet.doPost(request, response);

        verify(dataToJson).processResponse(response, 422, ErrorResponseBinding.ERROR_RESPONSE_422);

    }

    @Test
    public void whenFormIsValidAndCustomerExistsExpectStatus201() throws EntityNotFoundException {

        Customer customer = new Customer();

        customer.setId(101);
        customer.setName("Alex");
        customer.setLogin("aslex2");
        customer.setRole(Role.ROLE_ADMIN);

        when(requestBinding.toEntityObject()).thenReturn(customer);
        when(requestBinding.getPassword1()).thenReturn("!12*Alex&");
        when(requestBinding.getPassword2()).thenReturn("!12*Alex&");
        when(requestBinding.getName()).thenReturn("Alexander");
        when(requestBinding.getLogin()).thenReturn("markR12w");

        when(registerService.createNewCustomerInDb(requestBinding.toEntityObject())).thenReturn(customer);

        servlet.doPost(request, response);


        verify(dataToJson).processResponse(response, 201, new CustomerResponseBinding(customer));
    }

    @Test
    public void whenFormIsValidAndCustomerNotExistsExpectStatus404() throws EntityNotFoundException {

        when(requestBinding.getPassword1()).thenReturn("!12*Alex&");
        when(requestBinding.getPassword2()).thenReturn("!12*Alex&");
        when(requestBinding.getName()).thenReturn("Alexander");
        when(requestBinding.getLogin()).thenReturn("markR12w");

        doThrow(new EntityNotFoundException()).when(registerService).createNewCustomerInDb(Matchers.any(Customer.class));

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