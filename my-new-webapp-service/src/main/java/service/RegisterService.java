package service;

public interface RegisterService {
    int createNewCustomerInDb(String login, String name, String password);
}
