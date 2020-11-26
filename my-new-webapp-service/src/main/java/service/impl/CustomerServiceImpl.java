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
    public ArrayList<Customer> searchCustomers(Customer customer) {

        ArrayList<Customer> c;

        String login = customer.getLogin();
        Integer id = customer.getId();

        return cd.getCustomerByIdOrLogin(login, id);

    }

    @Override
    public ArrayList<Customer> outAllCustomers() {
        return cd.getCustomers();
    }
}
