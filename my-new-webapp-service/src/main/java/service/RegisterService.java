package service;

import database.CustomerDAO;
import database.CustomerDAOImpl;
import database.entity.Customer;

public class RegisterService {

    private CustomerDAO cd;

    public RegisterService() {
        this.cd = new CustomerDAOImpl();
    }

    public int toService(String login, String name, String password){

        Customer newCustomer;

        newCustomer = new Customer();
        newCustomer.setName(name);
        newCustomer.setLogin(login);
        newCustomer.setPassword(password);
        cd.insertCustomer(newCustomer);
        return 0;

    }
}
