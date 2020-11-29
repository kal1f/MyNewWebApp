package servlet;

import binding.request.CustomerWelcomeRequestBinding;
import binding.response.CustomersResponseBinding;
import binding.response.ErrorResponseBinding;
import database.entity.Customer;
import exception.CustomerNotFoundException;
import org.apache.log4j.Logger;
import service.CustomerService;
import service.impl.CustomerServiceImpl;
import util.DataToJson;
import util.JsonToData;
import util.validator.DataValidator;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;


@WebServlet(name = "/customers")
public class CustomerServlet extends HttpServlet {

    private CustomerService customerService;
    private DataToJson dataToJson;
    private JsonToData jsonToData;
    private DataValidator dataValidator;

    static final Logger LOGGER = Logger.getLogger(CustomerServlet.class);

    public CustomerServlet() {
        super();
    }

    public CustomerServlet(CustomerService customerService, DataToJson dataToJson,
                           DataValidator dataValidator, JsonToData jsonToData) {
        super();
        this.customerService = customerService;
        this.dataToJson = dataToJson;
        this.dataValidator = dataValidator;
        this.jsonToData = jsonToData;
    }

    @Override
    public void init(){
        this.customerService = new CustomerServiceImpl();
        this.dataToJson = new DataToJson();
        this.dataValidator = new DataValidator();
        this.jsonToData = new JsonToData();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    {
        CustomerWelcomeRequestBinding requestBinding = null;
        try {
            requestBinding = jsonToData.jsonToWelcomeData(request);
        } catch (IOException e){
            LOGGER.debug(e.getMessage(), e);
            dataToJson.processResponse(response, 422, ErrorResponseBinding.ERROR_RESPONSE_422);
            return;
        }

        if(requestBinding == null){
            ArrayList<Customer> c = customerService.getAllCustomers();
            dataToJson.processResponse(response, 200, new CustomersResponseBinding(c));
        }
        else if(dataValidator.isWelcomeFormValid(requestBinding.getId(),requestBinding.getLogin())) {

            try {
                ArrayList<Customer> c = customerService.searchCustomers(requestBinding.toCustomer());
                dataToJson.processResponse(response,200, new CustomersResponseBinding(c));
            } catch (CustomerNotFoundException e) {
                LOGGER.debug("Customers with login:"+requestBinding.getLogin()+
                        " id: "+requestBinding.getId()+
                        " are not existing");
                dataToJson.processResponse(response,404, ErrorResponseBinding.ERROR_RESPONSE_404);
            }

        }
        else{
            LOGGER.debug("Login or id is not valid");

            dataToJson.processResponse(response, 400, new ErrorResponseBinding(400,
                    "Login or ID is not valid"));

        }

    }


}
