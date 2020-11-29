package service;

import database.entity.Customer;
import exception.CustomerNotFoundException;

import java.util.ArrayList;

public interface CustomerService {
    ArrayList<Customer> searchCustomers(Customer customer) throws CustomerNotFoundException;
    ArrayList<Customer> getAllCustomers();
}
