package service.impl;

import database.CustomerDAO;
import database.CustomerDAOImpl;
import database.entity.Customer;
import exception.CustomerNotFoundException;
import org.apache.log4j.Logger;
import service.CustomerService;

import java.util.ArrayList;

public class CustomerServiceImpl implements CustomerService {

    private final CustomerDAO cd;

    static final Logger LOGGER = Logger.getLogger(CustomerServiceImpl.class);

    public CustomerServiceImpl() {
        cd = new CustomerDAOImpl();
    }

    public CustomerServiceImpl(CustomerDAO customerDAO){
        this.cd = customerDAO;
    }

    @Override
    public ArrayList<Customer> searchCustomers(Customer customer) throws CustomerNotFoundException{
        String login = customer.getLogin();
        Integer id = customer.getId();

        ArrayList<Customer> customers = cd.getCustomerByIdOrLogin(login, id);
        if(customers.isEmpty()){
            LOGGER.debug("getCustomerByIdOrLogin(String login, int id) returned empty array");
            throw new CustomerNotFoundException("Search customers returned empty array");
        }
        return customers;
    }

    @Override
    public ArrayList<Customer> getAllCustomers() {
        return cd.getCustomers();
    }
}
