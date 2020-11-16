package service.impl;

import database.CustomerDAO;
import database.CustomerDAOImpl;
import database.entity.Customer;
import service.RegisterService;

public class RegisterServiceImpl implements RegisterService {

    private CustomerDAO cd;

    public RegisterServiceImpl() {
        this.cd = new CustomerDAOImpl();
    }

    @Override
    public int createNewCustomerInDb(String login, String name, String password){

        Customer newCustomer;

        newCustomer = new Customer();
        newCustomer.setName(name);
        newCustomer.setLogin(login);
        newCustomer.setPassword(password);
        cd.insertCustomer(newCustomer);
        return 0;

    }
}
