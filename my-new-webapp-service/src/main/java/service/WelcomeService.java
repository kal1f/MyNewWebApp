package service;

import service.authentication.Authentication;
import service.authentication.AuthenticationImpl;

public class WelcomeService {
    private Authentication authentication;

    public WelcomeService() {
        this.authentication = new AuthenticationImpl();
    }
    public WelcomeService(Authentication authentication){
        this.authentication = authentication;
    }

}
