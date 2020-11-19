package servlet;


import service.LogoutService;
import service.authentication.AuthenticationImpl;
import service.impl.LogoutServiceImpl;


import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet(name = "/logout")
public class LogoutServlet extends HttpServlet {

    private LogoutService logoutService;

    public LogoutServlet() {
        super();
        logoutService = new LogoutServiceImpl();
    }

    public LogoutServlet(LogoutService logoutService) {
        super();
        this.logoutService = logoutService;
    }

    @Override
    public void init(){

       this.logoutService = new LogoutServiceImpl((AuthenticationImpl) getServletContext().getAttribute("authenticationImpl"));
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        response.setContentType("application/json");
        HttpSession session = request.getSession();
        logoutService.removeCustomerBySessionId(session.getId());
        session.invalidate();
        response.setStatus(200);
        response.sendRedirect("/login");

    }
}

