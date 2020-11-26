package database.entity;

public class Customer {

    private Integer id;
    private String login;
    private String name;
    private String password;

    public Customer(){

    }

    public Customer(int id, String login) {
        this.id = id;
        this.login = login;
    }

    public Customer(String login, String password){
        this.login = login;
        this.password = password;
    }
    public Customer(String login, String name, String password){
        this.login = login;
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

}
