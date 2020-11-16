package service;

import service.authentication.Authentication;
import service.authentication.AuthenticationImpl;



public class LogoutService {

    private Authentication authentication;

    public LogoutService() {
        this.authentication = new AuthenticationImpl();
    }

    public LogoutService(Authentication authentication){
        this.authentication = authentication;
    }

    public void toService(String session_id){
        authentication.removeCustomer(session_id);
    }

}
