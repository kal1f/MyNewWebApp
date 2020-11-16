package service;

import database.CustomerDAO;
import database.CustomerDAOImpl;
import database.entity.Customer;
import service.authentication.Authentication;
import service.authentication.AuthenticationImpl;


public class LoginService {

    private CustomerDAO cd;
    private Authentication authentication;

    public LoginService() {
        this.cd = new CustomerDAOImpl();
        this.authentication = new AuthenticationImpl();
    }

    public LoginService(Authentication authentication){
        this.cd = new CustomerDAOImpl();
        this.authentication = authentication;
    }

    public int toService(String session_id, String login, String password) {
        Customer customer = new Customer();
        customer.setLogin(login);
        customer.setPassword(password);

        if(cd.isCustomerExist(login, password)){

            authentication.setCustomer(session_id, customer);

            return 0;

        }
        else{
            return 1;

        }

    }
}
