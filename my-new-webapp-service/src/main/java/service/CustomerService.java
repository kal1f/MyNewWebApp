package service;

import database.entity.Customer;

import java.util.ArrayList;

public interface CustomerService {
    ArrayList<Customer> searchCustomers(Customer customer);
    ArrayList<Customer> outAllCustomers();
}
