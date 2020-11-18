package service.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import service.authentication.Authentication;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class LogoutServiceImplTest {
    @Mock
    Authentication authentication;

    @Test
    public void whenSessionIdIsNotNullThenRemoveCustomer(){
        LogoutServiceImpl logoutService = new LogoutServiceImpl(authentication);

        doNothing().when(authentication).removeCustomer(anyString());

        long status = logoutService.removeCustomerBySessionId("HGFVGBHN31ADs2");

        verify(authentication, times(1)).removeCustomer(anyString());
        assertEquals(0, status);
    }

    @Test
    public void whenSessionIdIsNullThenReturnInt(){

        LogoutServiceImpl logoutService = new LogoutServiceImpl(authentication);

        long status = logoutService.removeCustomerBySessionId(null);

        assertEquals(-1, status);
    }

}