package servlet;

import binding.response.ErrorResponseBinding;
import binding.request.CustomerRegisterRequestBinding;
import org.apache.log4j.Logger;
import service.RegisterService;
import service.impl.RegisterServiceImpl;
import util.DataToJson;
import util.JsonToData;
import util.validator.DataValidator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "/register")
public class RegisterServlet extends HttpServlet {

    private RegisterService registerService;
    private DataValidator dataValidator;
    private DataToJson dataToJson;
    private JsonToData jsonToData;


    static final Logger LOGGER = Logger.getLogger(RegisterServlet.class);


    public RegisterServlet() {
        super();
    }

    public RegisterServlet(DataToJson dataToJson){
        super();
        this.dataToJson = dataToJson;
    }

    public RegisterServlet(RegisterService registerService, DataToJson dataToJson,
                           DataValidator dataValidator, JsonToData jsonToData) {
        super();
        this.registerService = registerService;
        this.dataValidator = dataValidator;
        this.dataToJson = dataToJson;
        this.jsonToData = jsonToData;
    }


    @Override
    public void init(){

        registerService = new RegisterServiceImpl();
        dataValidator = new DataValidator();
        dataToJson = new DataToJson();
        jsonToData = new JsonToData();

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        try {
            request.getRequestDispatcher("register.html").forward(request, response);
        }catch (ServletException | IOException e){
            dataToJson.processResponse(response,
                    ErrorResponseBinding.ERROR_RESPONSE_500);
            LOGGER.error(e.getMessage(), e);
        }

    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        CustomerRegisterRequestBinding requestBinding= null;
        try {
            requestBinding = jsonToData.jsonToRegisterData(request);
        }catch (IOException e){
            LOGGER.debug(e.getMessage(), e);
            dataToJson.processResponse(response, 500,
                    ErrorResponseBinding.ERROR_RESPONSE_500);
            return;
        }

        if(dataValidator.isRegisterFormValid(requestBinding.getLogin(), requestBinding.getName(),
                requestBinding.getPassword1(), requestBinding.getPassword2())) {

            registerService.createNewCustomerInDb(requestBinding.toCustomer());
            dataToJson.processResponse(response, 201, null);
        }
        else{
            LOGGER.info("Customer with login:"+
                    requestBinding.getLogin()+" name: "+
                    requestBinding.getName()+" password1 "+
                    requestBinding.getPassword1()+" password2 "+
                    requestBinding.getPassword2()+" can not be registered");

            dataToJson.processResponse(response, 400,
                    new ErrorResponseBinding(400,
                            "Customer with login:"+requestBinding.getLogin()+
                                    " name: "+requestBinding.getName()+
                                    " password1: "+requestBinding.getPassword1()+
                                    " password2: "+requestBinding.getPassword2()+
                                    " can not be registered"));
        }
    }
}
