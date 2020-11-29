package service.impl;

import database.CustomerDAO;
import database.entity.Customer;
import exception.CustomerNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import service.RegisterService;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class RegisterServiceImplTest {
    @Mock
    CustomerDAO cd;


    RegisterService registerService;

    @Before
    public void setUp(){
        registerService = new RegisterServiceImpl(cd);

    }

    @Test
    public void createNewCustomerInDbAndReturnCustomerWhenAllParamsAreNotNull() throws CustomerNotFoundException {

        when(cd.insertCustomer(new Customer("login12", "myname", "pass1234!"))).thenReturn(120);

        Customer c = registerService.createNewCustomerInDb(new Customer("login12", "myname", "pass1234!"));

        assertEquals("login12", c.getLogin());
        assertEquals("myname", c.getName());
        assertEquals("pass1234!", c.getPassword());
    }

    @Test
    public void notCreateCustomerWhenOneOfParamsIsNull(){

        String statusOfNullLogin = null;
        try {
            Customer customer = registerService.createNewCustomerInDb(new Customer(null,"dad","Dsa"));
        } catch (CustomerNotFoundException e) {
            statusOfNullLogin = e.getMessage();
        }

        String statusOfNullPass = null;
        try {
            Customer customer = registerService.createNewCustomerInDb(new Customer("alex",null,"Dsa"));
        } catch (CustomerNotFoundException e) {
            statusOfNullPass = e.getMessage();
        }

        String statusOfNullName = null;
        try {
            Customer customer = registerService.createNewCustomerInDb(new Customer("alex","dad",null));
        } catch (CustomerNotFoundException e) {
            statusOfNullName = e.getMessage();
        }

        String statusOfNullLoginAndName = null;
        try {
            Customer customer = registerService.createNewCustomerInDb(new Customer(null,null,"Dsa"));
        } catch (CustomerNotFoundException e) {
            statusOfNullLoginAndName = e.getMessage();
        }

        String statusOfNullLoginAndPass = null;
        try {
            Customer customer = registerService.createNewCustomerInDb(new Customer(null,"dad",null));
        } catch (CustomerNotFoundException e) {
            statusOfNullLoginAndPass = e.getMessage();
        }

        String statusOfNullNameAndLogin = null;
        try {
            Customer customer = registerService.createNewCustomerInDb(new Customer("alex",null,null));
        } catch (CustomerNotFoundException e) {
            statusOfNullNameAndLogin = e.getMessage();
        }

        String statusOfNullParams = null;
        try {
            Customer customer = registerService.createNewCustomerInDb(new Customer(null,null,null));
        } catch (CustomerNotFoundException e) {
            statusOfNullParams = e.getMessage();
        }

        assertEquals("Customer with id=0 is not exists", statusOfNullParams);
        assertEquals("Customer with id=0 is not exists", statusOfNullLogin);
        assertEquals("Customer with id=0 is not exists", statusOfNullPass);
        assertEquals("Customer with id=0 is not exists", statusOfNullName);
        assertEquals("Customer with id=0 is not exists", statusOfNullLoginAndName);
        assertEquals("Customer with id=0 is not exists", statusOfNullLoginAndPass);
        assertEquals("Customer with id=0 is not exists", statusOfNullNameAndLogin);

    }



}