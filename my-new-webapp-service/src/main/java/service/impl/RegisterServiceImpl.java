package service.impl;

import database.dao.CustomerDAO;
import database.dao.impl.CustomerDAOImpl;
import database.entity.Customer;
import exception.EntityNotFoundException;
import org.apache.log4j.Logger;
import service.RegisterService;

import java.util.Properties;

public class RegisterServiceImpl implements RegisterService {

    private CustomerDAO cd;

    static final Logger LOGGER = Logger.getLogger(RegisterServiceImpl.class);

    public RegisterServiceImpl(Properties properties) {
        this.cd = new CustomerDAOImpl(properties);
    }

    public RegisterServiceImpl(CustomerDAO customerDAO){
        this.cd = customerDAO;
    }

    @Override
    public Customer createNewCustomerInDb(Customer customer) throws EntityNotFoundException {

        int id = cd.insertCustomer(customer);

        if(id != 0){
            customer.setId(id);
            return customer;
        }
        else {
            LOGGER.debug("Customer was not created in database");
            throw new EntityNotFoundException("Customer with id=0 is not exists");
        }

    }
}