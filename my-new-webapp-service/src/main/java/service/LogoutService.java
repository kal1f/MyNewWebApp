package service;

public interface LogoutService {
    int removeCustomerBySessionId(String session_id);
}
