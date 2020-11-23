package service;

import database.entity.Customer;

public interface LoginService {
    Customer authenticate(String sessionId, String login, String password);
}
