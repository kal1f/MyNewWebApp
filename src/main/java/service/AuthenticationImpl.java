package service;

import database.entity.Customer;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class AuthenticationImpl implements Authentication {

    private Map<String, Customer> customerProfileData = new ConcurrentHashMap<>();

    @Override
    public boolean isSessionPresent(String session_id) {

        return customerProfileData.containsKey(session_id);
    }

    @Override
    public boolean isUserAuthenticated(String session_id, Customer customer) {

        if(!isSessionPresent(session_id)) return false;

        Customer object = customerProfileData.get(session_id);

        return (customer.getPassword().equals(object.getPassword()) && customer.getLogin().equals(object.getLogin()));

    }

    @Override
    public Customer getCustomer(String session_id) {

        return customerProfileData.get(session_id);
    }

    @Override
    public void setCustomer(String session_id, Customer customer) {
        customerProfileData.put(session_id, customer);
    }

    @Override
    public void removeCustomer(String session_id) {
        if(isSessionPresent(session_id)) {
            customerProfileData.remove(session_id);
        }
    }
}
