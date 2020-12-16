package servlet;

import binding.response.CustomerResponseBinding;
import binding.response.ErrorResponseBinding;
import binding.request.CustomerRequestBinding;
import database.entity.Customer;
import exception.EntityNotFoundException;
import org.apache.log4j.Logger;
import service.RegisterService;
import service.impl.RegisterServiceImpl;
import util.DataToJson;
import util.JsonToData;
import util.validator.DataValidator;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Properties;

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

        registerService = new RegisterServiceImpl((Properties) getServletContext().getAttribute("properties"));
        dataValidator = new DataValidator();
        dataToJson = new DataToJson();
        jsonToData = new JsonToData();

    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        CustomerRequestBinding requestBinding;
        try {
            requestBinding = jsonToData.jsonToRegisterData(request);
        }catch (IOException e){
            LOGGER.debug(e.getMessage(), e);
            dataToJson.processResponse(response, 422,
                    ErrorResponseBinding.ERROR_RESPONSE_422);
            return;
        }

        if(dataValidator.isCustomerDataValid(requestBinding.getLogin(), requestBinding.getName(),
                requestBinding.getPassword1(), requestBinding.getPassword2())) {

            try {
                Customer customer = registerService.createNewCustomerInDb(requestBinding.toEntityObject());

                LOGGER.debug("Customer is created");

                dataToJson.processResponse(response, 201, new CustomerResponseBinding(customer));
            }catch (EntityNotFoundException e){
                LOGGER.debug("Customer is not created", e);

                dataToJson.processResponse(response, 404, ErrorResponseBinding.ERROR_RESPONSE_404);
            }

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