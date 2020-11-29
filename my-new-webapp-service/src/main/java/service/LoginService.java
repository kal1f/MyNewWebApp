package service;

import database.entity.Customer;
import exception.CustomerNotFoundException;

public interface LoginService {
    Customer authenticate(String sessionId, Customer customer) throws CustomerNotFoundException;
}
