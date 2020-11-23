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
    public int unauthenticate(String sessionId){
        if(sessionId != null){
            authentication.removeCustomer(sessionId);
        }
        else {
            return -1;
        }
        return 0;
    }

}
