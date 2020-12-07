package service;

import database.entity.Customer;
import exception.EntityNotFoundException;

public interface RegisterService {
    Customer createNewCustomerInDb(Customer customer) throws EntityNotFoundException;
}
