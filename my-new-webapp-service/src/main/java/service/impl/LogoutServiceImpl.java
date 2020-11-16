package service.impl;

import service.LogoutService;
import service.authentication.Authentication;
import service.authentication.AuthenticationImpl;



public class LogoutServiceImpl implements LogoutService {

    private Authentication authentication;

    public LogoutServiceImpl() {
        this.authentication = new AuthenticationImpl();
    }

    public LogoutServiceImpl(Authentication authentication){
        this.authentication = authentication;
    }

    @Override
    public void removeCustomerBySessionId(String session_id){ authentication.removeCustomer(session_id); }

}