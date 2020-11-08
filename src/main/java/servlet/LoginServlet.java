package servlet;

import database.CustomerDAO;
import database.CustomerDAOImpl;
import database.entity.Customer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import service.Authentication;
import service.AuthenticationImpl;
import utils.JsonHandler;


@WebServlet(name = "/login")
public class LoginServlet extends HttpServlet {

    private CustomerDAO cd;
    private Authentication authenticationImpl;
    private Customer customer;
    String msg;

    @Override
    public void init(){
        this.authenticationImpl = (AuthenticationImpl) getServletContext().getAttribute("authenticationImpl");
        cd = new CustomerDAOImpl();
        customer = new Customer();
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.getRequestDispatcher("login.html").forward(request, response);

    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws  ServletException, IOException {

        String login = request.getParameter("login");
        String password = request.getParameter("password1");

        customer.setLogin(login);
        customer.setPassword(password);

        if(cd.isCustomerExist(login, password) ){

            String session_id = request.getSession().getId();
            authenticationImpl.setCustomer(session_id, customer);

            response.setStatus(200);
            response.sendRedirect("/welcome");
        }
        else{
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");
            response.setHeader("cache-control", "no-cache");
            response.setStatus(404);
            JsonHandler.sendResponse(response, msg);
        }

    }
}
