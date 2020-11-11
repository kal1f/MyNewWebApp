package servlet;

import service.Authentication;
import service.AuthenticationImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet(name = "/logout")
public class LogoutServlet extends HttpServlet {

    private Authentication authentication;

    public LogoutServlet() {
        super();
    }

    public LogoutServlet(Authentication authentication) {
        super();
        this.authentication = authentication;
    }

    @Override
    public void init(){
        if(authentication == null) {
            this.authentication = (AuthenticationImpl) getServletContext().getAttribute("authenticationImpl");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("application/json");

        HttpSession session = request.getSession();

        authentication.removeCustomer(session.getId());
        session.invalidate();

        response.setStatus(200);
        response.sendRedirect("/login");

    }
}

