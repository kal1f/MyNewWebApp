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

    public RegisterServiceImpl(CustomerDAO customerDAO){
        this.cd = customerDAO;
    }

    @Override
    public int createNewCustomerInDb(Customer customer){

        String name = customer.getName();
        String login = customer.getLogin();
        String password = customer.getPassword();

        if(login != null && name!=null && password!=null) {
            Customer newCustomer;
            newCustomer = new Customer();
            newCustomer.setName(name);
            newCustomer.setLogin(login);
            newCustomer.setPassword(password);
            cd.insertCustomer(newCustomer);
            return 0;
        }
        else {
            return -1;
        }
    }
}
