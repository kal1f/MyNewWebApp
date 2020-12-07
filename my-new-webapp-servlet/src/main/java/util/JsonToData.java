package util;

import binding.request.*;
import com.google.gson.Gson;
import database.entity.Transaction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JsonToData {

    private Gson gson;


    public JsonToData() {
        this.gson = new Gson();
    }

    public JsonToData(Gson gson) {
        this.gson = gson;
    }

    public LoginRequestBinding jsonToLoginData(HttpServletRequest request) throws IOException {
        return gson.fromJson(request.getReader(), LoginRequestBinding.class);
    }

    public CustomerRequestBinding jsonToRegisterData(HttpServletRequest request) throws IOException {
        return gson.fromJson(request.getReader(), CustomerRequestBinding.class);
    }

    public CustomerSearchRequestBinding jsonToWelcomeData(HttpServletRequest request) throws IOException {
        return gson.fromJson(request.getReader(), CustomerSearchRequestBinding.class);
    }

    public CustomerUpdateRequestBinding jsonToCustomerUpdateData(HttpServletRequest request) throws IOException{
        return gson.fromJson(request.getReader(), CustomerUpdateRequestBinding.class);
    }

    public ProductRequestBinding jsonToProductData(HttpServletRequest request) throws IOException {
        return gson.fromJson(request.getReader(), ProductRequestBinding.class);
    }

    public ProductUpdateRequestBinding jsonToProductUpdateData(HttpServletRequest request) throws IOException {
        return gson.fromJson(request.getReader(), ProductUpdateRequestBinding.class);
    }

    public TransactionRequestBinding jsonToTransactionData(HttpServletRequest request) throws IOException{
        return gson.fromJson(request.getReader(), TransactionRequestBinding.class);
    }

    public TransactionUpdateRequestBinding jsonToTransactionUpdateData(HttpServletRequest request) throws IOException{
        return gson.fromJson(request.getReader(), TransactionUpdateRequestBinding.class);
    }

    public RoleRequestBinding jsonToRoleData(HttpServletRequest request) throws IOException{
        return gson.fromJson(request.getReader(), RoleRequestBinding.class);
    }

    public RoleUpdateRequestBinding jsonToRoleUpdateData(HttpServletRequest request) throws IOException{
        return gson.fromJson(request.getReader(), RoleUpdateRequestBinding.class);
    }
}
