package service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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

    @Override
    public String returnExistedUserInJson(String session_id, String login, String password) throws JsonProcessingException {
        Customer customer = new Customer();
        customer.setLogin(login);
        customer.setPassword(password);

        if(cd.isCustomerExist(login, password)){

            authentication.setCustomer(session_id, customer);

            ObjectMapper mapper = new ObjectMapper();

            Customer existedCustomer = cd.getCustomer(login, password);
            existedCustomer.setPassword("");

            return mapper.writeValueAsString(existedCustomer);

        }

        return null;
    }
}
