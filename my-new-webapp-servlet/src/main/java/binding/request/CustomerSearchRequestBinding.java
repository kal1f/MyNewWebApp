package binding.request;

import com.google.gson.annotations.JsonAdapter;
import database.entity.Customer;
import util.json.deserializer.IntegerDeserializer;

import java.util.Objects;


public class CustomerSearchRequestBinding implements RequestBinding {

    @JsonAdapter(IntegerDeserializer.class)
    private Integer id;
    private String login;

    public CustomerSearchRequestBinding(Integer id, String login) {
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
    public Customer toEntityObject() {

        return new Customer(id, login);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomerSearchRequestBinding that = (CustomerSearchRequestBinding) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(login, that.login);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, login);
    }
}
