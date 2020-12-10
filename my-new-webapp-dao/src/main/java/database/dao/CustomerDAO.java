package database.dao;

import database.entity.Customer;

import java.util.ArrayList;

public interface CustomerDAO {

    int insertCustomer(Customer c);
    Customer getCustomer(String username, String password);
    int updateCustomer(Customer customer, Integer id);
    ArrayList<Customer> getCustomerByIdOrLogin(String login, Integer id);
    ArrayList<Customer> getCustomers();
}