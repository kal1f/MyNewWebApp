//package service.impl;
//
//import database.CustomerDAO;
//import database.entity.Customer;
//import org.junit.Before;
//import org.junit.Ignore;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.Mock;
//import org.mockito.runners.MockitoJUnitRunner;
//import service.CustomerService;
//import java.util.ArrayList;
//
//import static org.junit.Assert.*;
//import static org.mockito.Mockito.*;
//
//@RunWith(MockitoJUnitRunner.class)
//@Ignore
//public class CustomerServiceImplTest {
//
//    @Mock
//    CustomerDAO cd;
//
//    CustomerService customerService;
//
//    ArrayList<Customer> customers;
//
//    @Before
//    public void setUp(){
//        customerService = new CustomerServiceImpl(cd);
//        customers = new ArrayList<>();
//        customers.add(new Customer("ale1x", "Alexand", "123!wq", 201));
//        customers.add(new Customer("lada12", "Lada", "21xve!wq", 202));
//        customers.add(new Customer("marina43", "Marina", "1!wlaw", 204));
//
//        when(cd.getCustomers()).thenReturn(customers);
//        when(cd.getCustomerByIdOrLogin("da41xd", 101)).thenReturn(new ArrayList<>());
//        when(cd.getCustomerByIdOrLogin(null, 101)).thenReturn(new ArrayList<>());
//        when(cd.getCustomerByIdOrLogin("da41xd", null)).thenReturn(new ArrayList<>());
//        when(cd.getCustomerByIdOrLogin(null, 201)).thenReturn(customers);
//        when(cd.getCustomerByIdOrLogin("lada12", null)).thenReturn(customers);
//        when(cd.getCustomerByIdOrLogin("lada12", 201)).thenReturn(customers);
//        when(cd.getCustomerByIdOrLogin(null, null)).thenReturn(customers);
//
//
//    }
//
//    @Test
//    public void returnArrayCustomersWhenIdIsNullAndLoginIsNull() {
//
//        ArrayList<Customer> customers = customerService.searchCustomers(null,null);
//
//        verify(cd, times(1)).getCustomers();
//        assertEquals(3, customers.size());
//    }
//
//    @Test
//    public void returnArrayCustomersWhenIdExistsAndLoginExists(){
//
//        customers.remove(2);
//        ArrayList<Customer> c = customerService.searchCustomers(201, "lada12");
//
//        verify(cd, times(1)).getCustomerByIdOrLogin("lada12", 201);
//        assertEquals(2, c.size());
//    }
//
//    @Test
//    public void returnListWhenIdIsNotExistingAndLogin(){
//        customers.remove(0);
//        customers.remove(1);
//
//        ArrayList<Customer> customers = customerService.searchCustomers(null, "lada12");
//
//        verify(cd, times(1)).getCustomerByIdOrLogin("lada12", null);
//
//        assertEquals(1, customers.size());
//    }
//
//    @Test
//    public void returnListWhenLoginExistsAndIdNull(){
//        customers.remove(1);
//        customers.remove(1);
//
//        ArrayList<Customer> c = customerService.searchCustomers(201, null);
//
//        verify(cd, times(1)).getCustomerByIdOrLogin(null, 201);
//
//        assertEquals(1, c.size());
//    }
//
//    @Test
//    public void returnEmptyArrayCustomersWhenIdIsNotExistingAndLoginNull(){
//
//        ArrayList<Customer> customers = customerService.searchCustomers(101, null);
//
//        verify(cd, times(1)).getCustomerByIdOrLogin(null, 101);
//
//        assertEquals(0, customers.size());
//    }
//
//
//    @Test
//    public void returnEmptyArrayCustomersWhenLoginIsNotExistingAndIdNull(){
//
//        ArrayList<Customer> customers = customerService.searchCustomers(null, "da41xd");
//
//        verify(cd, times(1)).getCustomerByIdOrLogin("da41xd", null);
//
//        assertEquals(0, customers.size());
//    }
//
//    @Test
//    public void returnArrayCustomersWhenIdNotExistsAndLoginNotExists(){
//
//        ArrayList<Customer> customers = customerService.searchCustomers(101, "da41xd");
//
//        verify(cd, times(1)).getCustomerByIdOrLogin("da41xd", 101);
//
//        assertEquals(0, customers.size());
//    }
//
//
//}
