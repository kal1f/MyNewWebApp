package binding.request;

import database.entity.Customer;

public class LoginRequestBinding implements RequestBinding {

    private String login;
    private String password;

    public LoginRequestBinding(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public Customer toEntityObject(){

        return new Customer(login, password);
    }

}
