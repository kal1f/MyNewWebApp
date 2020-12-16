package service.impl;

import database.dao.CustomerDAO;
import database.dao.impl.CustomerDAOImpl;
import database.entity.Customer;
import exception.EntityNotFoundException;
import org.apache.log4j.Logger;
import service.CustomerService;

import java.util.ArrayList;
import java.util.Properties;

public class CustomerServiceImpl implements CustomerService {

    private final CustomerDAO cd;

    static final Logger LOGGER = Logger.getLogger(CustomerServiceImpl.class);

    public CustomerServiceImpl(Properties properties) {
        cd = new CustomerDAOImpl(properties);
    }

    public CustomerServiceImpl(CustomerDAO customerDAO){
        this.cd = customerDAO;
    }

    @Override
    public ArrayList<Customer> searchCustomers(Customer customer) throws EntityNotFoundException {
        String login = customer.getLogin();
        Integer id = customer.getId();

        ArrayList<Customer> customers = cd.getCustomerByIdOrLogin(login, id);
        if(customers.isEmpty()){
            LOGGER.debug("getCustomerByIdOrLogin(String login, int id) returned empty array");
            throw new EntityNotFoundException("Search customers returned empty array");
        }
        return customers;
    }

    @Override
    public ArrayList<Customer> getAllCustomers() {
        return cd.getCustomers();
    }

    @Override
    public Customer processLogin(Customer customer, Integer id) throws EntityNotFoundException {

        int rowsUpdated = cd.updateCustomer(customer, id);

        if(rowsUpdated == 0){
            LOGGER.debug("updateCustomer(customer, id) returned 0");
            throw new EntityNotFoundException("Was not been updated any rows");
        }
        String login = customer.getLogin();
        return cd.getCustomer(login);
    }

}