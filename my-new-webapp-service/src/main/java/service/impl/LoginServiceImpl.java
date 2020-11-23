package service.impl;

import database.CustomerDAO;
import database.CustomerDAOImpl;
import database.entity.Customer;
import service.LoginService;
import service.authentication.Authentication;
import service.authentication.AuthenticationImpl;


public class LoginServiceImpl implements LoginService {

    private CustomerDAO cd;
    private Authentication authentication;

    public LoginServiceImpl() {
        this.cd = new CustomerDAOImpl();
        this.authentication = new AuthenticationImpl();
    }

    public LoginServiceImpl(Authentication authentication){
        this.cd = new CustomerDAOImpl();
        this.authentication = authentication;
    }
    public LoginServiceImpl(Authentication authentication, CustomerDAO customerDAO){
        this.cd = customerDAO;
        this.authentication = authentication;
    }

    @Override
    public Customer authenticate(String sessionId, String login, String password) {
        Customer customer = new Customer();
        customer.setLogin(login);
        customer.setPassword(password);

        if(cd.isCustomerExist(login, password)){

            authentication.setCustomer(sessionId, customer);

            Customer existedCustomer = cd.getCustomer(login, password);
            existedCustomer.setPassword(null);

            return existedCustomer;
        }

        return null;
    }

}
