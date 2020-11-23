package service.impl;

import database.CustomerDAO;
import database.entity.Customer;
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

        when(cd.isCustomerExist("alex2","132saw2!")).thenReturn(true);

        when(cd.isCustomerExist(null,null)).thenReturn(false);
        when(cd.isCustomerExist("alex2",null)).thenReturn(false);
        when(cd.isCustomerExist(null,"132saw2!")).thenReturn(false);
        when(cd.getCustomer("alex2", "132saw2!")).thenReturn(new Customer("alex2",
                "132saw2!", "Marko", 101));

        doNothing().when(authentication).setCustomer("SHDGSF22XCD21", customer);

    }


    @Test
    public void authenticateCustomerWhenPassAndLoginAreNotNull(){

        Customer authenticated = loginService.authenticate("SHDGSF22XCD21","alex2",
                "132saw2!");

        verify(cd).isCustomerExist("alex2", "132saw2!");
        verify(cd).getCustomer("alex2", "132saw2!");

        assertEquals(customer.getLogin(), authenticated.getLogin());
        assertEquals(null, authenticated.getPassword());
        assertEquals(101, authenticated.getId());
        assertEquals("Marko", authenticated.getName());

    }
    @Test
    public void notAuthenticateWhenPassAndLoginAreNull(){

        Customer authenticated = loginService.authenticate("SHDGSF22XCD21",null,
                null);

        verify(cd).isCustomerExist(null, null);


        assertNull(authenticated);
    }

    @Test
    public void notAuthenticateWhenPassNullLoginNotNull(){

        Customer authenticated = loginService.authenticate("SHDGSF22XCD21","login",null);

        verify(cd).isCustomerExist(anyString(), (String)isNull());

        assertNull(authenticated);
    }

    @Test
    public void notAuthenticateWhenPassNotNullAndLoginNull() {

        Customer authenticated = loginService.authenticate("SHDGSF22XCD21",null,"132saw2!");

        verify(cd).isCustomerExist((String) isNull(), anyString());

        assertNull(authenticated);
    }


}