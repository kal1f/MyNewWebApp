package binding.request;

import database.entity.Customer;

public class CustomerUpdateRequestBinding implements RequestBinding {
    private Integer id;
    private CustomerRequestBinding customer;

    public CustomerUpdateRequestBinding(Integer id, CustomerRequestBinding customer) {
        this.id = id;
        this.customer = customer;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public CustomerRequestBinding getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerRequestBinding customer) {
        this.customer = customer;
    }

    @Override
    public Customer toEntityObject() {
        return new Customer(this.customer.getLogin(), this.customer.getName(), this.customer.getPassword1());
    }
}
