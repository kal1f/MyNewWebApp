package service.impl;

import org.apache.log4j.Logger;
import service.LogoutService;
import service.authentication.Authentication;
import service.authentication.AuthenticationImpl;


public class LogoutServiceImpl implements LogoutService {

    private Authentication authentication;

    static final Logger LOGGER = Logger.getLogger(LogoutServiceImpl.class);

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
            LOGGER.debug("User was not authenticated");
            return -1;
        }
        return 0;
    }

}
