package service.authentication;
import database.entity.Customer;

public interface Authentication {
    boolean isSessionPresent(String sessionId);
    boolean isUserAuthenticated(String sessionId, Customer customer);
    Customer getCustomer(String sessionId);
    void setCustomer(String sessionId, Customer customer);
    void removeCustomer(String sessionId);
}
