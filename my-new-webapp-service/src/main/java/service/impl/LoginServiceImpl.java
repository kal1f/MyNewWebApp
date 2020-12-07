package service.impl;

import database.dao.CustomerDAO;
import database.dao.impl.CustomerDAOImpl;
import database.entity.Customer;
import exception.EntityNotFoundException;
import org.apache.log4j.Logger;
import service.LoginService;
import service.authentication.Authentication;
import service.authentication.AuthenticationImpl;


public class LoginServiceImpl implements LoginService {

    private final CustomerDAO cd;
    private final Authentication authentication;

    static final Logger LOGGER = Logger.getLogger(LoginServiceImpl.class);

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
    public Customer authenticate(String sessionId, Customer customer) throws EntityNotFoundException {
        String login = customer.getLogin();
        String password = customer.getPassword();

        Customer existedCustomer = cd.getCustomer(login, password);

        if(existedCustomer != null){

            authentication.setCustomer(sessionId, customer);

            existedCustomer.setPassword(null);

            return existedCustomer;
        }
        else {
            LOGGER.debug("getCustomer(String login, String password) returned null");
            throw new EntityNotFoundException("Customer with login: "+login+", pass: "+password+" is not exists");
        }

    }

}
