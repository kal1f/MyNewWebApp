package util.validator;


public class DataValidator {

    public boolean isNotNull(String parameter){
        return parameter != null;
    }

    public boolean isLengthValid(String parameter, int length){
        if(parameter == null){
            return false;
        }
       return parameter.length() >= length;
    }

    public boolean isPasswordValid(String password){
        if (password == null){
            return false;
        }
        return password.matches("^[0-9a-zA-Z!@#$%^&*()]+$");
    }

    public boolean isLoginValid(String login){
        if(login == null){
            return false;
        }

        return login.matches("^[a-zA-Z][0-9a-zA-Z]+$");

    }

    public boolean isNameValid(String name){
        if(name == null){
            return false;
        }
        return name.matches("^[a-zA-Z]*$");
    }

    public boolean isIdValid(Integer id){
        if(id == null || id < 0){
            return false;
        }

        return true;

    }

    public boolean arePasswordsEqual(String password1, String password2){
        if(password1 == null || password2 == null)
        {
            return false;
        }
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

    public boolean isWelcomeFormValid(Integer id, String login){
        if(login == null || id == null){
            return false;
        }

        if((id == 0 || id == -1) && login.equals("")){
            return false;
        }

        return (isIdValid(id) &&
                (isLoginValid(login) && isLengthValid(login,6)) || login.equals("") );
    }

}
