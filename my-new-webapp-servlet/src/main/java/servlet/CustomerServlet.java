package servlet;

import service.CustomerService;
import service.impl.CustomerServiceImpl;
import util.ResponseHandlerToJson;
import util.validator.DataValidator;


import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;



@WebServlet(name = "/customers")
public class CustomerServlet extends HttpServlet {

    private CustomerService customerService;
    private ResponseHandlerToJson responseHandlerToJson;
    private DataValidator dataValidator = new DataValidator();

    public CustomerServlet() {
        super();
    }

    public CustomerServlet(CustomerService customerService, ResponseHandlerToJson responseHandlerToJson) {
        super();
        this.customerService = customerService;
        this.responseHandlerToJson = responseHandlerToJson;
    }

    @Override
    public void init(){
        this.customerService = new CustomerServiceImpl();
        this.responseHandlerToJson = new ResponseHandlerToJson();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws  IOException {

        String login = request.getParameter("login");
        String id = request.getParameter("id");

        if(dataValidator.isWelcomeFormValid(id, login)) {
            this.responseHandlerToJson.processResponse(response, 200, customerService.returnCustomers(convertStringToInteger(id), login));
        }
        else{
            this.responseHandlerToJson.processResponse(response, 400, null);
        }

    }

    private Integer convertStringToInteger(String value){
        try{
            return Integer.parseInt(value);
        }catch(NumberFormatException e){
            //log
            return null;
        }
    }

}
