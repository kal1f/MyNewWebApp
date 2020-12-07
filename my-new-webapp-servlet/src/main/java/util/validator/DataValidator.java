package util.validator;


import binding.request.*;

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
        return status.equals("CREATED") || status.equals("PAID") ||
                status.equals("BOXING") || status.equals("DELIVERING") ||
                status.equals("COMPLETE");
    }

    public boolean isTransactionPaymentTypeValid(String paymentType){
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

    public boolean isCustomerUpdateDataValid(CustomerUpdateRequestBinding binding){
        return isIdValid(binding.getId()) && isCustomerDataValid(
                binding.getCustomer().getLogin(),
                binding.getCustomer().getName(),
                binding.getCustomer().getPassword1(),
                binding.getCustomer().getPassword1());
    }

    public boolean isProductDataValid(String name, String category){
        return isNameValid(name) && isNameValid(category);
    }

    public boolean isProductUpdateDataValid(ProductUpdateRequestBinding binding){
        return isIdValid(binding.getId()) && isProductDataValid(binding.getProduct().getName(),
                binding.getProduct().getCategory());
    }

//    public boolean isWelcomeFormValid(Integer id, String login){
//        if(login == null || id == null){
//            return false;
//        }
//
//        if((id == 0 || id == -1) && login.equals("")){
//            return false;
//        }
//
//        return (isIdValid(id) &&
//                (isLoginValid(login) && isLengthValid(login,6)) || login.equals("") );
//    }
    public boolean isWelcomeDataValid(String id, String login){

        return ((isIdValid(id) || id == null) &&
                ((isLoginValid(login) && isLengthValid(login,6)) || login.equals("")));
    }

    public boolean isTransactionDataValid(TransactionRequestBinding binding){
        return isIdValid(binding.getCustomerId()) &&
                isIdValid(binding.getProductId()) &&
                isTransactionPaymentTypeValid(binding.getPaymentType());
    }

    public boolean isTransactionUpdateDataValid(TransactionUpdateRequestBinding binding){
        return isIdValid(binding.getId()) && isTransactionStatusValid(binding.getStatus());
    }

    public boolean isRoleDataValid(RoleRequestBinding binding){
        return isNameValid(binding.getName());
    }

    public boolean isRoleUpdateDataValid(RoleUpdateRequestBinding binding){
        return isNameValid(binding.getName()) && isIdValid(binding.getId());
    }

}
