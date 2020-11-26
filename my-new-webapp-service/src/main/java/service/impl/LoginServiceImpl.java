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
    public Customer authenticate(String sessionId, Customer customer) {
        String login = customer.getLogin();
        String password = customer.getPassword();

        Customer existedCustomer = cd.getCustomer(login, password);

        if(existedCustomer != null){

            authentication.setCustomer(sessionId, customer);

            existedCustomer.setPassword(null);

            return existedCustomer;
        }

        return null;
    }

}
