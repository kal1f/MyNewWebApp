package util;

import binding.request.CustomerLoginRequestBinding;
import binding.request.CustomerRegisterRequestBinding;
import binding.request.CustomerWelcomeRequestBinding;
import com.google.gson.Gson;

import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class JsonToData {

    private Gson gson;

    static final Logger LOGGER = Logger.getLogger(JsonToData.class);

    public JsonToData() {
        this.gson = new Gson();
    }

    public JsonToData(Gson gson) {
        this.gson = gson;
    }

    public CustomerLoginRequestBinding jsonToLoginData(HttpServletRequest request) throws IOException {
        return gson.fromJson(request.getReader(), CustomerLoginRequestBinding.class);
    }

    public CustomerRegisterRequestBinding jsonToRegisterData(HttpServletRequest request) throws IOException {
        return gson.fromJson(request.getReader(), CustomerRegisterRequestBinding.class);
    }

    public CustomerWelcomeRequestBinding jsonToWelcomeData(HttpServletRequest request) throws IOException {
        return gson.fromJson(request.getReader(), CustomerWelcomeRequestBinding.class);
    }

}
