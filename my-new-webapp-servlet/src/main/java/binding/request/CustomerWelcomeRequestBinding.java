package binding.request;

import com.google.gson.annotations.JsonAdapter;
import database.entity.Customer;
import util.json.deserializer.IntegerDeserializer;

import java.util.Objects;


public class CustomerWelcomeRequestBinding implements RequestBinding {

    @JsonAdapter(IntegerDeserializer.class)
    private Integer id;
    private String login;

    public CustomerWelcomeRequestBinding(Integer id, String login) {
        this.id = id;
        this.login = login;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public Integer getId() {

        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public Customer toCustomer() {

        return new Customer(id, login);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomerWelcomeRequestBinding that = (CustomerWelcomeRequestBinding) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(login, that.login);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, login);
    }
}
