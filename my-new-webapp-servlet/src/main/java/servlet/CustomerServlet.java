package servlet;

import org.apache.log4j.Logger;
import service.CustomerService;
import service.impl.CustomerServiceImpl;
import util.HttpResponseModel;
import util.ResponseHandlerToJson;
import util.validator.DataValidator;


import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet(name = "/customers")
public class CustomerServlet extends HttpServlet {

    private CustomerService customerService;
    private ResponseHandlerToJson responseHandlerToJson;
    private HttpResponseModel httpResponseModel;
    private DataValidator dataValidator;

    static final Logger LOGGER = Logger.getLogger(CustomerServlet.class);

    public CustomerServlet() {
        super();
    }

    public CustomerServlet(CustomerService customerService, ResponseHandlerToJson responseHandlerToJson,
                           HttpResponseModel httpResponseModel, DataValidator dataValidator) {
        super();
        this.customerService = customerService;
        this.responseHandlerToJson = responseHandlerToJson;
        this.dataValidator = dataValidator;
        this.httpResponseModel = httpResponseModel;
    }

    @Override
    public void init(){
        this.customerService = new CustomerServiceImpl();
        this.responseHandlerToJson = new ResponseHandlerToJson();
        this.dataValidator = new DataValidator();
        this.httpResponseModel = new HttpResponseModel();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    {
        String login = request.getParameter("login");
        String id = request.getParameter("id");

        if(dataValidator.isWelcomeFormValid(id, login)) {
            httpResponseModel.setStatus(200);
            httpResponseModel.setMessage("Ok");
            httpResponseModel.setCustomers(customerService.searchCustomers(convertStringToInteger(id), login));
            responseHandlerToJson.processResponse(response, httpResponseModel);
        }
        else{
            LOGGER.warn("Login or id is not valid");
            httpResponseModel.setStatus(400);
            httpResponseModel.setMessage("Login or id is not valid");
            responseHandlerToJson.processResponse(response, httpResponseModel);

        }

    }

    public Integer convertStringToInteger(String value){
        try{
            LOGGER.debug("Parse "+value);
            return Integer.parseInt(value);
        }catch(NumberFormatException e){
            //log
            LOGGER.error(e.getMessage(), e);
            //
            return null;
        }
    }

}
