package service.impl;

import database.CustomerDAO;
import database.entity.Customer;
import exception.CustomerNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import service.LoginService;
import service.authentication.Authentication;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;


@RunWith(MockitoJUnitRunner.class)
public class LoginServiceImplTest {

    @Mock
    CustomerDAO cd;
    @Mock
    Authentication authentication;

    LoginService loginService;

    Customer customer = new Customer();

    @Before
    public void setUp(){
        customer.setLogin("alex2");
        customer.setPassword("132saw2!");

        loginService = new LoginServiceImpl(authentication, cd);

        when(cd.getCustomer("alex2", "132saw2!")).thenReturn(new Customer("alex2",
                "132saw2!", "Marko", 101));

        doNothing().when(authentication).setCustomer("SHDGSF22XCD21", customer);

    }


    @Test
    public void authenticateCustomerWhenPassAndLoginAreNotNull() throws CustomerNotFoundException {

        Customer authenticated = loginService.authenticate("SHDGSF22XCD21",new Customer("alex2", "132saw2!"));

        verify(cd).getCustomer("alex2", "132saw2!");

        assertEquals(customer.getLogin(), authenticated.getLogin());
        assertNull(authenticated.getPassword());
        assertEquals(101, authenticated.getId());
        assertEquals("Marko", authenticated.getName());

    }

    @Test
    public void notAuthenticateWhenPassAndLoginAreNull(){
        String message = null;

        try {
           loginService.authenticate("SHDGSF22XCD21",
                   new Customer((String) null, null));
        } catch (CustomerNotFoundException e) {
            message = e.getMessage();
        }

        verify(cd).getCustomer(null, null);

        assertEquals("Customer with login: null, pass: null is not exists", message);
    }

    @Test
    public void notAuthenticateWhenPassNullLoginNotNull() {

        String message = null;
        try {
           loginService.authenticate("SHDGSF22XCD21",
                   new Customer("login", null));
        } catch (CustomerNotFoundException e) {
            message = e.getMessage();
        }

        verify(cd).getCustomer(anyString(), (String)isNull());

        assertEquals("Customer with login: login, pass: null is not exists", message);
    }

    @Test
    public void notAuthenticateWhenPassNotNullAndLoginNull(){

        String message = null;

        try {
           loginService.authenticate("SHDGSF22XCD21",
                    new Customer((String) null, "132saw2!"));
        } catch (CustomerNotFoundException e) {
            message = e.getMessage();
        }

        verify(cd).getCustomer((String) isNull(), anyString());

        assertEquals("Customer with login: null, pass: 132saw2! is not exists", message);
    }


}
