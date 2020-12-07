package binding.request;

import database.entity.Customer;

public class CustomerRequestBinding implements RequestBinding {

    private String login;
    private String name;
    private String password1;
    private String password2;


    public CustomerRequestBinding(String login, String name,
                                  String password1, String password2) {
        this.login = login;
        this.name = name;
        this.password1 = password1;
        this.password2 = password2;
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

    public void setName(String name) { this.name = name; }

    public String getPassword1() {
        return password1;
    }

    public void setPassword1(String password1) {
        this.password1 = password1;
    }

    public String getPassword2() {
        return password2;
    }

    public void setPassword2(String password2) {
        this.password2 = password2;
    }

    @Override
    public Customer toEntityObject() {
        return new Customer(login, name, password1);
    }
}
