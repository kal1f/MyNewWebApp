package servlet;

import service.authentication.AuthenticationImpl;
import service.WelcomeService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "/welcome")
public class WelcomeServlet extends HttpServlet {

    private WelcomeService welcomeService;

    @Override
    public void init(){
        this.welcomeService = new WelcomeService((AuthenticationImpl) getServletContext().getAttribute("authenticationImpl"));
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.sendRedirect("/welcome.html");

    }

}
