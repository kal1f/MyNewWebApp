package service;

import database.entity.Customer;

public interface RegisterService {
    int createNewCustomerInDb(Customer customer);
}
