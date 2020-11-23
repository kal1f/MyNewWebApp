package service;

import database.entity.Customer;

import java.util.ArrayList;

public interface CustomerService {
    ArrayList<Customer> searchCustomers(Integer id, String login);
}
