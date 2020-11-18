package util.validator;


public class DataValidator {

    public boolean isPasswordValid(String password){
        if(password == null){
            return false;
        }
        if(password.length() >= 8 ){
            for(int i = 0; i<password.length(); i++){

                char character = password.charAt(i);
                if(((character >= '0' && character<='9') ||
                        (character >= 'a' && character <= 'z') ||
                        (character >= 'A' && character <= 'Z')) ||
                        (Character.toString(character).matches("[!@#$%^&*()]"))){
                    char a = character;
                }
                else{
                    return false;
                }
            }
        }
        else {
            return false;
        }
        return true;
    }


    public boolean isLoginValid(String login){

        if(login == null){
            return false;
        }
        if(login.length() >= 6) {
            for(int i = 0; i < login.length(); i++) {

                char character = login.charAt(i);
                if(((character >= '0' && character <= '9') ||
                                (character >= 'a' && character <= 'z') ||
                                (character >= 'A' && character <= 'Z'))) {

                }
                else {
                    return false;
                }
            }
        }
        else{
            return false;
        }
        return true;
    }


    public boolean isNameValid(String name){
        if(name == null){
            return false;
        }
        if(name.length() >= 2){
            for(int i = 0; i < name.length(); i++){

                char character = name.charAt(i);
                if((character == ' ' ||
                        (character >= 'a' && character <= 'z') ||
                        (character >= 'A' && character <= 'Z'))) {

                }
                else {
                    return false;
                }
            }
        }
        else {
            return false;
        }
        return true;
    }


    public boolean isIdValid(String id){
        if(id == null){
            return false;
        }

        for(int i = 0; i < id.length(); i++){

            char character = id.charAt(i);
            if(character >= '0' && character<='9'){
            }
            else{
                return false;
            }
        }
        return true;
    }


    public boolean arePasswordsEqual(String password1, String password2){
        return password1.equals(password2);
    }

    public boolean isLogInFormValid(String login, String password){
        return isLoginValid(login) && isPasswordValid(password);
    }

    public boolean isRegisterFormValid(String login, String name, String password1, String password2){
        return isLoginValid(login) && isPasswordValid(password1) && isNameValid(name) && arePasswordsEqual(password1, password2);
    }

    public boolean isWelcomeFormValid(String id, String login){
        return (id == null && login == null) ||
                (id == null && isLoginValid(login)) ||
                (isIdValid(id) && login == null) ||
                (isIdValid(id) && isLoginValid(login));
    }
}
