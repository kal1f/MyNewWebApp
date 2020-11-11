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
import utils.ResponseHandlerToJson;


@WebServlet(name = "/login")
public class LoginServlet extends HttpServlet {

    private CustomerDAO cd;
    private Authentication authentication;
    private ResponseHandlerToJson responseHandlerToJson;

    public LoginServlet() {
        super();
    }

    public LoginServlet(CustomerDAO cd, Authentication authentication) {
        super();
        this.cd = cd;
        this.authentication = authentication;
    }

    public LoginServlet(CustomerDAO cd, Authentication authentication, ResponseHandlerToJson responseHandlerToJson) {
        super();
        this.cd = cd;
        this.authentication = authentication;
        this.responseHandlerToJson = responseHandlerToJson;
    }

    @Override
    public void init(){
        if(cd == null) {
            cd = new CustomerDAOImpl();
        }
        if(authentication == null) {
            this.authentication = (AuthenticationImpl) getServletContext().getAttribute("authenticationImpl");
        }
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.getRequestDispatcher("login.html").forward(request, response);

    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws  ServletException, IOException {

        String login = request.getParameter("login");
        String password = request.getParameter("password1");
        Customer customer = new Customer();
        customer.setLogin(login);
        customer.setPassword(password);

        if(cd.isCustomerExist(login, password)){

            HttpSession session = request.getSession();
            authentication.setCustomer(session.getId(), customer);

            response.setStatus(200);
            response.sendRedirect("/welcome");
        }
        else{
            if(responseHandlerToJson == null){
                responseHandlerToJson = new ResponseHandlerToJson(response);
            }
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");
            response.setHeader("cache-control", "no-cache");

            responseHandlerToJson.processResponse(404, null);
        }

    }
}
