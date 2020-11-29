package service.impl;

import database.CustomerDAO;
import database.CustomerDAOImpl;
import database.entity.Customer;
import exception.CustomerNotFoundException;
import org.apache.log4j.Logger;
import service.RegisterService;

public class RegisterServiceImpl implements RegisterService {

    private CustomerDAO cd;

    static final Logger LOGGER = Logger.getLogger(RegisterServiceImpl.class);

    public RegisterServiceImpl() {
        this.cd = new CustomerDAOImpl();
    }

    public RegisterServiceImpl(CustomerDAO customerDAO){
        this.cd = customerDAO;
    }

    @Override
    public Customer createNewCustomerInDb(Customer customer) throws CustomerNotFoundException {

        int id = cd.insertCustomer(customer);

        if(id != 0){
            customer.setId(id);
            return customer;
        }
        else {
            LOGGER.debug("Customer was not created in database");
            throw new CustomerNotFoundException("Customer with id=0 is not exists");
        }

    }
}
