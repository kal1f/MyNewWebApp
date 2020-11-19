package service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import database.CustomerDAO;
import database.entity.Customer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import service.LoginService;
import service.authentication.Authentication;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class LoginServiceImplTest {

    @Mock
    CustomerDAO cd;
    @Mock
    Authentication authentication;
    @Mock
    Customer customer;
    @Mock
    Customer c;
    @Mock
    ObjectMapper objectMapper;

    @Test
    public void setAuthenticationAndReturnJsonWhenPassAndLoginAreNotNull() throws JsonProcessingException {
        LoginService loginService = new LoginServiceImpl(authentication, cd);

        doCallRealMethod().when(customer).setLogin(anyString());
        doCallRealMethod().when(customer).setPassword(anyString());

        when(cd.isCustomerExist(anyString(),anyString())).thenReturn(true);

        doNothing().when(authentication).setCustomer(anyString(), eq(customer));

        when(cd.getCustomer(anyString(), anyString())).thenReturn(c);

        doNothing().when(c).setPassword("");

        doReturn("json").when(objectMapper).writeValueAsString(c);

        loginService.returnExistedUserInJson("SHDGSF22XCD21","login","password");

        verify(cd, times(1)).isCustomerExist(anyString(), anyString());
        verify(authentication, times(1)).setCustomer(anyString(), any(Customer.class));
        verify(cd, times(1)).getCustomer(anyString(), anyString());
    }
    @Test
    public void setAuthenticationAndReturnJsonWhenPassAndLoginAreNull() throws JsonProcessingException {
        LoginService loginService = new LoginServiceImpl(authentication, cd);

        doCallRealMethod().when(customer).setLogin(null);
        doCallRealMethod().when(customer).setPassword(null);

        when(cd.isCustomerExist(null,null)).thenReturn(true);

        doNothing().when(authentication).setCustomer(anyString(), eq(customer));

        when(cd.getCustomer(null,null)).thenReturn(c);

        doNothing().when(c).setPassword("");

        doReturn("json").when(objectMapper).writeValueAsString(c);

        loginService.returnExistedUserInJson("SHDGSF22XCD21",null,null);

        verify(cd, times(1)).isCustomerExist(null, null);
        verify(authentication, times(1)).setCustomer(anyString(), any(Customer.class));
        verify(cd, times(1)).getCustomer(null, null);
    }

    @Test
    public void setAuthenticationAndReturnJsonWhenPassNullLoginNotNull() throws JsonProcessingException {
        LoginService loginService = new LoginServiceImpl(authentication, cd);

        doCallRealMethod().when(customer).setLogin(anyString());
        doCallRealMethod().when(customer).setPassword(null);

        when(cd.isCustomerExist(anyString(),(String)isNull())).thenReturn(true);

        doNothing().when(authentication).setCustomer(anyString(), eq(customer));

        when(cd.getCustomer(anyString(), (String)isNull())).thenReturn(c);

        doNothing().when(c).setPassword("");

        doReturn("json").when(objectMapper).writeValueAsString(c);

        loginService.returnExistedUserInJson("SHDGSF22XCD21","login",null);

        verify(cd, times(1)).isCustomerExist(anyString(), (String)isNull());
        verify(authentication, times(1)).setCustomer(anyString(), any(Customer.class));
        verify(cd, times(1)).getCustomer(anyString(), (String)isNull());
    }

    @Test
    public void setAuthenticationAndReturnJsonWhenPassNotNullAndLoginNull() throws JsonProcessingException {
        LoginService loginService = new LoginServiceImpl(authentication, cd);

        doCallRealMethod().when(customer).setLogin(null);
        doCallRealMethod().when(customer).setPassword(anyString());

        when(cd.isCustomerExist((String)isNull(),anyString())).thenReturn(true);

        doNothing().when(authentication).setCustomer(anyString(), eq(customer));

        when(cd.getCustomer((String)isNull(), anyString())).thenReturn(c);

        doNothing().when(c).setPassword("");

        doReturn("json").when(objectMapper).writeValueAsString(c);

        loginService.returnExistedUserInJson("SHDGSF22XCD21",null,"password");

        verify(cd, times(1)).isCustomerExist((String)isNull(), anyString());
        verify(authentication, times(1)).setCustomer(anyString(), any(Customer.class));
        verify(cd, times(1)).getCustomer((String)isNull(), anyString());
    }
}