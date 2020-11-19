package service.impl;

import database.CustomerDAO;
import database.CustomerDAOImpl;
import database.entity.Customer;
import service.CustomerService;

import java.util.ArrayList;

public class CustomerServiceImpl implements CustomerService {

    private CustomerDAO cd;


    public CustomerServiceImpl() {
        cd = new CustomerDAOImpl();
    }

    public CustomerServiceImpl(CustomerDAO customerDAO){
        this.cd = customerDAO;
    }


    @Override
    public ArrayList<Customer> returnCustomers(Integer id, String login) {

        ArrayList<Customer> c;

        if (login != null || id != null) {

            c = cd.getCustomerByIdOrLogin(login, id);

        } else {

            c = cd.getCustomers();

        }
        return c;

    }
}
