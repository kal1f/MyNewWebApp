package database.entity;

public class Customer {

    private String id;
    private String login;
    private String name;
    private String password;

    public Customer(){

    }

    public Customer(String customer, String pass_, String name_, String id) {
        this.id = id;
        this.login = customer;
        this.password = pass_;
        this.name = name_;
    }


    public String getId() {
        return id;
    }

    public void setId(String username) {
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

}
