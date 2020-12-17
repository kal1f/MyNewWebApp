package service;

import database.entity.Customer;
import exception.EntityNotFoundException;

import java.math.BigInteger;
import java.util.ArrayList;

public interface CustomerService {
    ArrayList<Customer> searchCustomers(Customer customer) throws EntityNotFoundException;
    ArrayList<Customer> getAllCustomers();
    Customer updateCustomer(Customer customer, Integer id) throws EntityNotFoundException;
}
