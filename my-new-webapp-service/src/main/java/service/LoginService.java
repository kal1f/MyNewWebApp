package service;

import database.entity.Customer;
import exception.EntityNotFoundException;

public interface LoginService {
    Customer authenticate(String sessionId, Customer customer) throws EntityNotFoundException;
}
