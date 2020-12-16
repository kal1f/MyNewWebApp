package servlet;

import binding.request.CustomerUpdateRequestBinding;
import binding.response.CustomerResponseBinding;
import binding.response.CustomersResponseBinding;
import binding.response.ErrorResponseBinding;
import database.entity.Customer;
import exception.EntityNotFoundException;
import org.apache.log4j.Logger;
import service.CustomerService;
import service.impl.CustomerServiceImpl;
import util.DataToJson;
import util.JsonToData;
import util.validator.DataValidator;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;


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
        this.customerService = new CustomerServiceImpl((Properties) getServletContext().getAttribute("properties"));
        this.dataToJson = new DataToJson();
        this.dataValidator = new DataValidator();
        this.jsonToData = new JsonToData();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {

        String login = request.getParameter("login");
        String id = request.getParameter("id");

        if(login==null && id==null){
            ArrayList<Customer> c = customerService.getAllCustomers();
            dataToJson.processResponse(response, 200, new CustomersResponseBinding(c));
        }
        else if(dataValidator.isWelcomeDataValid(id,login)) {

            try {
                Customer customer = new Customer();
                customer.setId(id);
                customer.setLogin(login);
                ArrayList<Customer> c = customerService.searchCustomers(customer);
                dataToJson.processResponse(response,200, new CustomersResponseBinding(c));
            } catch (EntityNotFoundException e) {
                LOGGER.debug("Customers with login:"+login+
                        " id: "+id+
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

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) {
        CustomerUpdateRequestBinding requestBinding = null;

        try{
            requestBinding=jsonToData.jsonToCustomerUpdateData(request);
        }catch (IOException e){
            LOGGER.debug(e.getMessage(), e);
            dataToJson.processResponse(response, 422, ErrorResponseBinding.ERROR_RESPONSE_422);
            return;
        }

        if(dataValidator.isCustomerUpdateDataValid(requestBinding.getId(), requestBinding.getCustomer().getLogin(),
                requestBinding.getCustomer().getName(), requestBinding.getCustomer().getPassword1(),
                requestBinding.getCustomer().getPassword2())){
            try {
                Customer c = customerService.updateCustomer(requestBinding.toEntityObject(), requestBinding.getId());
                dataToJson.processResponse(response, 200, new CustomerResponseBinding(c));
            }catch (EntityNotFoundException e){
                LOGGER.debug("Customer with params"+
                        requestBinding.getId()+
                        "was not been updated" , e);
                dataToJson.processResponse(response, 404, ErrorResponseBinding.ERROR_RESPONSE_404);
            }
        }
        else {
            LOGGER.debug("Input data is not valid");

            dataToJson.processResponse(response, 400, new ErrorResponseBinding(400,
                    "Input data in not valid"));
        }
    }
}
