package servlet;

import service.Authentication;
import service.AuthenticationImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "/welcome")
public class WelcomeServlet extends HttpServlet {

    private Authentication authentication;

    @Override
    public void init(){
        this.authentication = (AuthenticationImpl) getServletContext().getAttribute("authenticationImpl");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendRedirect("/welcome.html");

    }

}
