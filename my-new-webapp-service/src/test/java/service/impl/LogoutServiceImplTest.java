/*
package service.impl;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import service.LogoutService;
import service.authentication.Authentication;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class LogoutServiceImplTest {

    @Mock
    Authentication authentication;

    LogoutService logoutService;

    @Before
    public void setUp(){
       logoutService = new LogoutServiceImpl(authentication);
       doNothing().when(authentication).removeCustomer(anyString());

    }

    @Test
    public void whenSessionIdIsNotNullThenRemoveCustomer(){

        long status = logoutService.unauthenticate("HGFVGBHN31ADs2");

        verify(authentication, times(1)).removeCustomer(anyString());

        assertEquals(0, status);
    }

    @Test
    public void whenSessionIdIsNullThenReturnInt(){

        long status = logoutService.unauthenticate(null);

        assertEquals(-1, status);
    }

}*/
