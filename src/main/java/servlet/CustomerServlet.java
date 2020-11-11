package servlet;

import database.CustomerDAO;
import database.CustomerDAOImpl;
import database.entity.Customer;
import utils.ResponseHandlerToJson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;


@WebServlet(name = "/customers")
public class CustomerServlet extends HttpServlet {

    //private static final long serialVersionUID = 1L;
    private CustomerDAO cd;
    private ResponseHandlerToJson responseHandlerToJson;

    public CustomerServlet() {
        super();
    }

    public CustomerServlet(CustomerDAO cd, ResponseHandlerToJson responseHandlerToJson) {
        super();
        this.cd = cd;
        this.responseHandlerToJson = responseHandlerToJson;
    }

    @Override
    public void init(){

        cd = new CustomerDAOImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String login = request.getParameter("login");
        String id = request.getParameter("id");

        ArrayList<Customer> c;

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        if (login != null || id != null) {

            c = cd.getCustomerByIdOrLogin(login, id);

        } else {

            c = cd.getCustomers();

        }

        if(responseHandlerToJson == null) {
            ResponseHandlerToJson responseHandlerToJson = new ResponseHandlerToJson(response);
            responseHandlerToJson.processResponse( 200, c);
        }
        else {
            //ResponseHandler.sendResponse(response, jsonString);
            responseHandlerToJson.processResponse(200, c);
        }
    }

}
