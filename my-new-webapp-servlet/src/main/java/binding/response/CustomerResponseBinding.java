package binding.response;

import database.entity.Customer;

import java.util.Objects;

public class CustomerResponseBinding implements ResponseBinding {
    private int id;
    private String login;
    private String name;

    public CustomerResponseBinding(int id, String login, String name) {
        this.id = id;
        this.login = login;
        this.name = name;
    }

    public CustomerResponseBinding(Customer customer) {
        this.id = customer.getId();
        this.login = customer.getLogin();
        this.name = customer.getName();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomerResponseBinding that = (CustomerResponseBinding) o;
        return id == that.id &&
                Objects.equals(login, that.login) &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, login, name);
    }
}
