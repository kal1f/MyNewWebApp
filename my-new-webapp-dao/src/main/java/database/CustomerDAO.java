package database;

import database.entity.Customer;

import java.util.ArrayList;

public interface CustomerDAO {

    int insertCustomer(Customer c);
    Customer getCustomer(String username, String password);
    ArrayList<Customer> getCustomerByIdOrLogin(String login, Integer id);
    ArrayList<Customer> getCustomers();
    boolean isCustomerExist(String login, String password);
}
