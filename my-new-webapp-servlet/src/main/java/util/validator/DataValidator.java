package util.validator;


public class DataValidator {

    public boolean isNotNull(String parameter){
        return parameter != null;
    }

    public boolean isLengthValid(String parameter, int length){
       return parameter.length() >= length;
    }

    public boolean isPasswordValid(String password){
        return password.matches("^[0-9a-zA-Z!@#$%^&*()]+$");
    }

    public boolean isLoginValid(String login){
        return login.matches("^[0-9a-zA-Z]+$");
    }

    public boolean isNameValid(String name){
        return name.matches("^[a-zA-Z]*$");
    }

    public boolean isIdValid(String id){
        return id.matches("\\d+");
    }

    public boolean arePasswordsEqual(String password1, String password2){
        return password1.equals(password2);
    }

    public boolean isLogInFormValid(String login, String password){
        return isLoginValid(login) &&
                isPasswordValid(password) &&
                isNotNull(login) && isNotNull(password) &&
                isLengthValid(login, 6) &&
                isLengthValid(password, 8);
    }

    public boolean isRegisterFormValid(String login, String name, String password1, String password2){
        return isLoginValid(login) &&
                isPasswordValid(password1) &&
                isNameValid(name) &&
                arePasswordsEqual(password1, password2) &&
                isNotNull(login) &&
                isNotNull(name) &&
                isNotNull(password1) &&
                isNotNull(password2) &&
                isLengthValid(login, 6) &&
                isLengthValid(password1, 8) &&
                isLengthValid(name, 2);

    }

    public boolean isWelcomeFormValid(String id, String login){
        return (id == null || isIdValid(id) || id.equals("")) &&
                (login == null || login.equals("") ||(isLoginValid(login) && isLengthValid(login,6)));
    }

}
