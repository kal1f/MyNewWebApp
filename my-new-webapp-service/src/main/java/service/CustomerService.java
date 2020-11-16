package service;

import database.CustomerDAO;
import database.CustomerDAOImpl;
import database.entity.Customer;


import java.util.ArrayList;

public class CustomerService {

    private CustomerDAO cd;


    public CustomerService() {
        cd = new CustomerDAOImpl();
    }

    public ArrayList<Customer> toService(String id, String login) {

        ArrayList<Customer> c;

        if (login != null || id != null) {

            c = cd.getCustomerByIdOrLogin(login, id);

        } else {

            c = cd.getCustomers();

        }
        return c;

    }
}
