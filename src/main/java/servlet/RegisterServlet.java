package servlet;

import database.CustomerDAO;
import database.CustomerDAOImpl;
import database.entity.Customer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "/register")
public class RegisterServlet extends HttpServlet {

    private CustomerDAO cd;

    @Override
    public void init(){
        cd = new CustomerDAOImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("register.html").forward(request, response);
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String login = request.getParameter("login");
        String name = request.getParameter("name");
        String password = request.getParameter("password1");
        String submitType = request.getParameter("submit");
        //Customer c = cd.getCustomer(login, password);
        Customer newCustomer;

        if(submitType.equals("register"))
        {
            newCustomer = new Customer();
            newCustomer.setName(name);
            newCustomer.setLogin(login);
            newCustomer.setPassword(password);
            cd.insertCustomer(newCustomer);
            response.setContentType("application/json");
            response.setContentType("UTF-8");
            response.setStatus(201);
            response.sendRedirect("login.html");
        }
    }
}
