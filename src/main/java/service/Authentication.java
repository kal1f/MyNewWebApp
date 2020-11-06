package service;
import database.entity.Customer;

public interface Authentication {
    boolean isSessionPresent(String session_id);
    boolean isUserAuthenticated(String session_id, Customer customer);
    Customer getCustomer(String session_id);
    void setCustomer(String session_id, Customer customer);
    void removeCustomer(String session_id);
}
