package service.authentication;

import database.entity.Customer;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class AuthenticationImpl implements Authentication {

    private final Map<String, Customer> customerProfileData;

    public AuthenticationImpl(){
        customerProfileData = new ConcurrentHashMap<>();
    }

    public AuthenticationImpl(Map<String, Customer> concurrentHashMap) {
        customerProfileData = concurrentHashMap;
    }

    @Override
    public boolean isSessionPresent(String sessionId) {

        if(sessionId == null){
            return false;
        }

        return customerProfileData.containsKey(sessionId);
    }

    @Override
    public boolean isUserAuthenticated(String sessionId, Customer customer) {

        if(customer == null || !isSessionPresent(sessionId)) {
            return false;
        }

        Customer object = customerProfileData.get(sessionId);

        return (customer.getPassword().equals(object.getPassword()) && customer.getLogin().equals(object.getLogin()));

    }

    @Override
    public Customer getCustomer(String sessionId) {

        return customerProfileData.get(sessionId);
    }

    @Override
    public void setCustomer(String sessionId, Customer customer) {
        if(sessionId == null || customer == null){
            return;
        }
        customerProfileData.put(sessionId, customer);
    }

    @Override
    public void removeCustomer(String sessionId) {
        if(isSessionPresent(sessionId)) {
            customerProfileData.remove(sessionId);
        }
    }
}
