package servlet;

import database.CustomerDAOImpl;
import database.entity.Customer;
import service.Authentication;
import service.AuthenticationImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet(name = "/logout")
public class LogoutServlet extends HttpServlet {

    Authentication authenticationImpl;

    @Override
    public void init(){
        this.authenticationImpl = (AuthenticationImpl) getServletContext().getAttribute("authenticationImpl");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("application/json");

        HttpSession session = request.getSession();

        authenticationImpl.removeCustomer(session.getId());
        session.invalidate();

        response.setStatus(200);
        response.sendRedirect("/login");

    }
}

