package binding.response;

import database.entity.Customer;

import java.util.ArrayList;
import java.util.Objects;

public class CustomersResponseBinding implements ResponseBinding {

    private ArrayList<Customer> customers;

    public CustomersResponseBinding(ArrayList<Customer> customers) {
        this.customers = customers;
    }

    public ArrayList<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(ArrayList<Customer> customers) {
        this.customers = customers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomersResponseBinding that = (CustomersResponseBinding) o;
        return Objects.equals(customers, that.customers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(customers);
    }
}
