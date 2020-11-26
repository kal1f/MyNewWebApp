/*package service.impl;

import database.CustomerDAO;
import database.entity.Customer;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import service.RegisterService;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class RegisterServiceImplTest {
    @Mock
    CustomerDAO cd;
    @Mock
    Customer customer;

    RegisterService registerService;

    @Before
    public void setUp(){
        registerService = new RegisterServiceImpl(cd);

        doCallRealMethod().when(customer).setLogin(anyString());
        doCallRealMethod().when(customer).setPassword(anyString());
        doCallRealMethod().when(customer).setName(anyString());

    }

    @Test
    public void createNewCustomerInDbAndReturnNullWhenAllParamsAreNotNull(){

       when(cd.insertCustomer(customer)).thenReturn(anyInt());

       long status = registerService.createNewCustomerInDb("21","dad","Dsa");

       verify(cd, times(1)).insertCustomer(any(Customer.class));
       assertEquals(0, status);
    }

    @Test
    public void notCreateCustomerAndReturnMinusValueWhenOneOfParamsAreNull(){

        long statusOfNullLogin = registerService.createNewCustomerInDb(null,"dad","Dsa");
        long statusOfNullPass = registerService.createNewCustomerInDb("alex",null,"Dsa");
        long statusOfNullName = registerService.createNewCustomerInDb("alex","dad",null);

        assertEquals(-1, statusOfNullLogin);
        assertEquals(-1, statusOfNullPass);
        assertEquals(-1, statusOfNullName);
    }


}*/