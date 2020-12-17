package service.impl;

import database.dao.CustomerDAO;
import database.entity.Customer;
import exception.EntityNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import service.CustomerService;
import java.util.ArrayList;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)

public class CustomerServiceImplTest {

    @Mock
    CustomerDAO cd;

    CustomerService customerService;

    ArrayList<Customer> customers;

    @Before
    public void setUp(){
        customerService = new CustomerServiceImpl(cd);
        customers = new ArrayList<>();
        customers.add(new Customer("ale1x", "Alexand", "123!wq", 201));
        customers.add(new Customer("lada12", "Lada", "21xve!wq", 202));
        customers.add(new Customer("marina43", "Marina", "1!wlaw", 204));

        when(cd.getCustomers()).thenReturn(customers);
        when(cd.getCustomerByIdOrLogin("da41xd", 101)).thenReturn(new ArrayList<>());
        when(cd.getCustomerByIdOrLogin(null, 101)).thenReturn(new ArrayList<>());
        when(cd.getCustomerByIdOrLogin("da41xd", 0)).thenReturn(new ArrayList<>());
        when(cd.getCustomerByIdOrLogin(null, 201)).thenReturn(customers);
        when(cd.getCustomerByIdOrLogin("lada12", 0)).thenReturn(customers);
        when(cd.getCustomerByIdOrLogin("lada12", 201)).thenReturn(customers);
        when(cd.getCustomerByIdOrLogin(null, 0)).thenReturn(new ArrayList<>());

        when(cd.updateCustomer(new Customer("lasf12","ahmed", "dsa12da!da2"), null)).thenReturn(0);
        when(cd.updateCustomer(new Customer(null,"ahmed", "dsa12da!da2"), 450)).thenReturn(0);
        when(cd.updateCustomer(new Customer("lasf12",null, "dsa12da!da2"), 450)).thenReturn(0);
        when(cd.updateCustomer(new Customer("lasf12","ahmed", null), 450)).thenReturn(0);
        when(cd.updateCustomer(new Customer("lasf12","ahmed", "dsa12da!da2"), 103)).thenReturn(1);

        when(cd.getCustomer(anyString())).thenReturn(new Customer("lasf12","dsa12da!da2", "ahmed", 103));
    }
    @Test
    public void getAllCustomers(){
        ArrayList<Customer> customers = customerService.getAllCustomers();

        verify(cd).getCustomers();

        assertEquals(3, customers.size());
        assertEquals(customers.get(0), new Customer("ale1x", "Alexand", "123!wq", 201));
        assertEquals(customers.get(1), new Customer("lada12", "Lada", "21xve!wq", 202));
        assertEquals(customers.get(2), new Customer("marina43", "Marina", "1!wlaw", 204));

    }

    @Test
    public void searchCustomersExpectEmptyArrayWhenIdIsNullAndLoginIsNull() throws EntityNotFoundException {
        ArrayList<Customer> customers;
        String message = null;

        try {
            customers = customerService.searchCustomers(new Customer(0, null));
        }catch (EntityNotFoundException e){
            message = e.getMessage();
        }

        verify(cd).getCustomerByIdOrLogin(null, 0);

        assertEquals("Search customers returned empty array", message);
    }

    @Test
    public void searchCustomersExpectArrayWhenIdExistsAndLoginExists() throws EntityNotFoundException {

        customers.remove(2);
        ArrayList<Customer> customers = customerService.searchCustomers(new Customer(201, "lada12"));

        verify(cd, times(1)).getCustomerByIdOrLogin("lada12", 201);
        assertEquals(2, customers.size());
        assertEquals(customers.get(0), new Customer("ale1x", "Alexand", "123!wq", 201));
        assertEquals(customers.get(1), new Customer("lada12", "Lada", "21xve!wq", 202));
    }

    @Test
    public void searchCustomersExpectArrayWhenIdIsNotExistingAndLogin() throws EntityNotFoundException {
        customers.remove(0);
        customers.remove(1);

        ArrayList<Customer> customers = customerService.searchCustomers(new Customer( 0, "lada12"));

        verify(cd, times(1)).getCustomerByIdOrLogin("lada12", 0);

        assertEquals(1, customers.size());
        assertEquals(customers.get(0), new Customer("lada12", "Lada", "21xve!wq", 202));
    }

    @Test
    public void searchCustomersExpectArrayWhenLoginExistsAndIdNull() throws EntityNotFoundException {
        customers.remove(1);
        customers.remove(1);

        ArrayList<Customer> c = customerService.searchCustomers(new Customer(201, null));

        verify(cd, times(1)).getCustomerByIdOrLogin(null, 201);

        assertEquals(1, c.size());
        assertEquals(customers.get(0), new Customer("ale1x", "Alexand", "123!wq", 201));
    }

    @Test
    public void searchCustomersExpectEmptyArrayWhenIdIsNotExistingAndLoginNull(){

        ArrayList<Customer> customers = null;
        String message = null;

        try {
            customers = customerService.searchCustomers(new Customer(101, null));
        } catch (EntityNotFoundException e) {
            message = e.getMessage();
        }

        verify(cd, times(1)).getCustomerByIdOrLogin(null, 101);

        assertEquals("Search customers returned empty array", message);
    }

    @Test
    public void searchCustomersExpectEmptyArrayWhenLoginIsNotExistingAndIdNull(){

        ArrayList<Customer> customers = null;
        String message = null;

        try {
            customers = customerService.searchCustomers(new Customer(0, "da41xd"));
        } catch (EntityNotFoundException e) {
            message = e.getMessage();
        }

        verify(cd, times(1)).getCustomerByIdOrLogin("da41xd", 0);

        assertEquals("Search customers returned empty array", message);
    }

    @Test
    public void searchCustomersExpectEmptyArrayWhenIdNotExistsAndLoginNotExisting()throws EntityNotFoundException {
        ArrayList<Customer> customers;
        String message = null;

        try {
            customers = customerService.searchCustomers(new Customer(101, "da41xd"));
        }catch (EntityNotFoundException e){
            message = e.getMessage();
        }

        verify(cd, times(1)).getCustomerByIdOrLogin("da41xd", 101);

        assertEquals("Search customers returned empty array", message);
    }

    @Test
    public void updateCustomerWithNullIdExpectEntityNotFoundException() {
        Customer customer;

        String message=null;

        try {
            customer = customerService.updateCustomer(
                    new Customer("lasf12","ahmed", "dsa12da!da2"), null);
        } catch (EntityNotFoundException e){
            message = e.getMessage();
        }

        assertEquals("Was not been updated any rows", message);
    }

    @Test
    public void updateCustomerWithNullLoginExpectEntityNotFoundException() {
        Customer customer;

        String message=null;

        try {
            customer = customerService.updateCustomer(
                    new Customer(null,"ahmed", "dsa12da!da2"), 450);
        } catch (EntityNotFoundException e){
            message = e.getMessage();
        }

        assertEquals("Was not been updated any rows", message);
    }

    @Test
    public void updateCustomerWithNullPasswordExpectEntityNotFoundException() {
        Customer customer;

        String message=null;

        try {
            customer = customerService.updateCustomer(
                    new Customer("lasf12",null, "dsa12da!da2"), 450);
        } catch (EntityNotFoundException e){
            message = e.getMessage();
        }

        assertEquals("Was not been updated any rows", message);
    }

    @Test
    public void updateCustomerWithNameExpectEntityNotFoundException() {
        Customer customer;

        String message=null;

        try {
            customer = customerService.updateCustomer(
                    new Customer("lasf12","ahmed", null), 450);
        } catch (EntityNotFoundException e){
            message = e.getMessage();
        }

        assertEquals("Was not been updated any rows", message);
    }

    @Test
    public void updateCustomerExpectCustomer() throws EntityNotFoundException {

        Customer customer = customerService.updateCustomer(new Customer("lasf12","ahmed", "dsa12da!da2"), 103);

        verify(cd).updateCustomer(Matchers.any(Customer.class), Matchers.any(Integer.class));

        assertEquals(103, customer.getId());
        assertEquals("lasf12",customer.getLogin());
        assertEquals("ahmed", customer.getName());
        assertEquals("dsa12da!da2", customer.getPassword());
    }


}
