package service.impl;

import database.dao.CustomerDAO;
import database.dao.impl.CustomerDAOImpl;
import database.entity.Customer;
import exception.EntityNotFoundException;
import org.apache.log4j.Logger;
import service.LoginService;
import service.authentication.Authentication;
import service.authentication.AuthenticationImpl;

import java.util.Properties;


public class LoginServiceImpl implements LoginService {

    private final CustomerDAO cd;
    private final Authentication authentication;

    static final Logger LOGGER = Logger.getLogger(LoginServiceImpl.class);

    public LoginServiceImpl(Properties properties) {
        this.cd = new CustomerDAOImpl(properties);
        this.authentication = new AuthenticationImpl();
    }

    public LoginServiceImpl(Authentication authentication, Properties properties){
        this.cd = new CustomerDAOImpl(properties);
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

        Customer existingCustomer = cd.processSignIn(login, password);

        if(existingCustomer != null){

            authentication.setCustomer(sessionId, existingCustomer);

            existingCustomer.setPassword(null);

            return existingCustomer;
        }
        else {
            LOGGER.debug("getCustomer(String login, String password) returned null");
            throw new EntityNotFoundException("Customer with login: "+login+", pass: "+password+" is not exists");
        }

    }

}
