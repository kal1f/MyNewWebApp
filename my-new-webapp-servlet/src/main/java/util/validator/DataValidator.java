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
        return id != null && id >= 0;
    }

    public boolean isIdValid(String id){
        int number;

        try {
            number = Integer.parseInt(id);
        }catch (NumberFormatException e){
            return false;
        }

        return number >= 0;
    }

    public boolean isTransactionStatusValid(String status){
        if(status == null){
            return false;
        }
        return status.equals("CREATED") || status.equals("PAID") ||
                status.equals("BOXING") || status.equals("DELIVERING") ||
                status.equals("COMPLETE");
    }

    public boolean isTransactionPaymentTypeValid(String paymentType){
        if(paymentType == null){
            return false;
        }
        return paymentType.equals("CREDITCARD") ||
                paymentType.equals("CASH") ||
                paymentType.equals("LOYALTY");
    }

    public boolean arePasswordsEqual(String password1, String password2){
        if(password1 == null || password2 == null)
        {
            return false;
        }
        return password1.equals(password2);
    }

    public boolean isLogInDataValid(String login, String password){
        return isLoginValid(login) &&
                isPasswordValid(password) &&
                isNotNull(login) && isNotNull(password) &&
                isLengthValid(login, 6) &&
                isLengthValid(password, 8);
    }

    public boolean isCustomerDataValid(String login, String name, String password1, String password2){
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

    public boolean isCustomerUpdateDataValid(Integer id, String login, String name, String password1, String password2){
        return isIdValid(id) && isCustomerDataValid(
                login,
                name,
                password1,
                password2);
    }

    public boolean isProductDataValid(String name, String category, Double price, Double priceDiscount){
        if(price == null){
            return  false;
        }
        return isNameValid(name) && isNameValid(category) && price >= 0 && (priceDiscount == null || priceDiscount >= 0);
    }

    public boolean isProductUpdateDataValid(Integer id, String name, String category, Double price, Double priceDiscount){
        return isIdValid(id) && isProductDataValid(name, category, price, priceDiscount);
    }

    public boolean isWelcomeDataValid(String id, String login){
        if(login == null || id == null){
            return false;
        }

        if(id.equals("") && login.equals("")){
            return false;
        }

        if(isIdValid(id) && login.equals("")){
            return true;
        }

        if(id.equals("") && (isLoginValid(login) && isLengthValid(login, 6))){
            return true;
        }

        //return isIdValid(id) && (isLoginValid(login) && isLengthValid(login, 6));
//        return ((isIdValid(id) || id == null || id.equals("")) &&
//                ((isLoginValid(login) && isLengthValid(login,6)) || login.equals("")));
        return (isIdValid(id) &&
                (isLoginValid(login) && isLengthValid(login,6)) || login.equals(""));
    }

    public boolean isTransactionDataValid(Integer customerId, Integer productId, String payment){
        return isIdValid(customerId) &&
                isIdValid(productId) &&
                isTransactionPaymentTypeValid(payment);
    }

    public boolean isTransactionUpdateDataValid(Integer id, String status){
        return isIdValid(id) && isTransactionStatusValid(status);
    }

    public boolean isRoleDataValid(String name){
        return isNameValid(name);
    }

    public boolean isRoleUpdateDataValid(Integer id, String name){
        return isNameValid(name) && isIdValid(id);
    }

}
