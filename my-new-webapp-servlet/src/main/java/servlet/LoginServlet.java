package servlet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import binding.request.LoginRequestBinding;
import binding.response.CustomerResponseBinding;
import binding.response.ErrorResponseBinding;
import database.entity.Customer;
import exception.EntityNotFoundException;
import org.apache.log4j.Logger;
import service.LoginService;
import service.authentication.AuthenticationImpl;
import service.impl.LoginServiceImpl;
import util.DataToJson;
import util.JsonToData;
import util.validator.DataValidator;


@WebServlet(name = "/login")
public class LoginServlet extends HttpServlet {

    private DataToJson dataToJson;
    private JsonToData jsonToData;
    private LoginService loginService;
    private DataValidator dataValidator;

    static final Logger LOGGER = Logger.getLogger(LoginServlet.class);

    public LoginServlet() {
        super();
    }

    public LoginServlet(DataToJson dataToJson){
        this.dataToJson = dataToJson;

    }

    public LoginServlet(LoginService loginService, DataToJson dataToJson,
                        DataValidator dataValidator, JsonToData jsonToData) {
        super();
        this.loginService = loginService;
        this.dataToJson = dataToJson;
        this.dataValidator = dataValidator;
        this.jsonToData = jsonToData;
    }

    @Override
    public void init() {
        this.loginService = new LoginServiceImpl((AuthenticationImpl) getServletContext().getAttribute("authenticationImpl"));
        this.dataToJson = new DataToJson();
        this.dataValidator = new DataValidator();
        this.jsonToData = new JsonToData();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        LoginRequestBinding requestBinding;
        try {
            requestBinding = jsonToData.jsonToLoginData(request);
        }catch (IOException e){
            LOGGER.debug(e.getMessage(), e);
            dataToJson.processResponse(response, 422,
                    ErrorResponseBinding.ERROR_RESPONSE_422);
            return;
        }

        HttpSession session = request.getSession();

        if(dataValidator.isLogInDataValid(requestBinding.getLogin(), requestBinding.getPassword())){

            try {
                Customer customer = loginService.authenticate(session.getId(), requestBinding.toEntityObject());

                LOGGER.debug("Customer is not null");

                dataToJson.processResponse(response, 200,
                        new CustomerResponseBinding(customer.getId(), customer.getLogin(), customer.getName(), customer.getRole()));
            } catch (EntityNotFoundException e) {

                LOGGER.debug("Customer was not found", e);

                dataToJson.processResponse(response, 404,ErrorResponseBinding.ERROR_RESPONSE_404);
            }

        }
        else {
            LOGGER.debug("Login: "+
                    requestBinding.getLogin()+
                    " or "+" password: "+
                    requestBinding.getPassword()+
                    "is not valid");

            dataToJson.processResponse(response, 400,
                    new ErrorResponseBinding(400,
                            "Login: "+
                                    requestBinding.getLogin()+
                                    " or password: "+
                                    requestBinding.getPassword()+
                                    " is not correct"));

        }
    }

}
