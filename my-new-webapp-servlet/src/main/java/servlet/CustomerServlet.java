package servlet;

import service.CustomerService;
import service.impl.CustomerServiceImpl;
import util.ResponseHandlerToJson;


import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;



@WebServlet(name = "/customers")
public class CustomerServlet extends HttpServlet {

    private CustomerService customerService;
    private ResponseHandlerToJson responseHandlerToJson;

    public CustomerServlet() {
        super();
    }

    public CustomerServlet(CustomerServiceImpl customerServiceImpl, ResponseHandlerToJson responseHandlerToJson) {
        super();
        this.customerService = customerServiceImpl;
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

        this.responseHandlerToJson.processResponse(response, 200, customerService.returnCustomers(id, login));
    }

}
