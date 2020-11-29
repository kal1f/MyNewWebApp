package database.entity;

import java.util.Objects;

public class Customer {

    private Integer id;
    private String login;
    private String name;
    private String password;

    public Customer(){

    }

    public Customer(Integer id, String login) {
        this.id = id;
        this.login = login;
    }

    public Customer(String login, String password){
        this.login = login;
        this.password = password;
    }

    public Customer(String login, String name, String password){
        this.login = login;
        this.name = name;
        this.password = password;
    }

    public Customer(String login, String pass_, String name_, Integer id) {
        this.id = id;
        this.login = login;
        this.password = pass_;
        this.name = name_;
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

    public void setLogin(String username) {
        this.login = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return Objects.equals(id, customer.id) &&
                Objects.equals(login, customer.login) &&
                Objects.equals(name, customer.name) &&
                Objects.equals(password, customer.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, login, name, password);
    }
}
