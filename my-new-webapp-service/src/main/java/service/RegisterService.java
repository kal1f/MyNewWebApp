package service;

import database.entity.Customer;
import exception.CustomerNotFoundException;

public interface RegisterService {
    Customer createNewCustomerInDb(Customer customer) throws CustomerNotFoundException;
}
