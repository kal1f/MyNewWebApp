package service.impl;

import database.CustomerDAO;
import database.entity.Customer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import service.CustomerService;
import java.util.ArrayList;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CustomerServiceImplTest {

    @Mock
    CustomerDAO cd;

    @Mock
    ArrayList<Customer> c;

    @Test
    public void returnArrayCustomersWhenIdIs0AndLoginIsNull() {

        CustomerService customerService = new CustomerServiceImpl(cd);

        when(cd.getCustomers()).thenReturn(c);

        customerService.returnCustomers(0,null);

        verify(cd, times(1)).getCustomers();
    }

    @Test
    public void returnArrayCustomersWhenIdIsIntegerAndLoginIsString(){
        CustomerService customerService = new CustomerServiceImpl(cd);

        when(cd.getCustomerByIdOrLogin(anyString(),anyInt())).thenReturn(c);

        customerService.returnCustomers(anyInt(), anyString());

        verify(cd, times(1)).getCustomerByIdOrLogin(anyString(), anyInt());
    }

    @Test
    public void returnArrayCustomersWhenIdIsIntegerAndLoginIsNull(){
        CustomerService customerService = new CustomerServiceImpl(cd);

        when(cd.getCustomerByIdOrLogin(null, 101)).thenReturn(c);

        customerService.returnCustomers(101, null);

        verify(cd, times(1)).getCustomerByIdOrLogin(null, 101);
    }

    @Test
    public void returnArrayCustomersWhenIdIsNullAndLoginIsString(){
        CustomerService customerService = new CustomerServiceImpl(cd);

        when(cd.getCustomerByIdOrLogin(anyString(), (Integer) isNull())).thenReturn(c);

        customerService.returnCustomers((Integer) isNull(), anyString());

        verify(cd, times(1)).getCustomerByIdOrLogin(anyString(), (Integer) isNull());

    }


}