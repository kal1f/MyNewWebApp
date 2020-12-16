package servlet;

import org.apache.log4j.Logger;
import service.LogoutService;
import service.authentication.AuthenticationImpl;
import service.impl.LogoutServiceImpl;
import util.DataToJson;
import javax.servlet.http.*;

public class LogoutServlet extends HttpServlet {

    private LogoutService logoutService;
    private DataToJson dataToJson;

    static final Logger LOGGER = Logger.getLogger(LogoutServlet.class);

    public LogoutServlet() {
        super();
        logoutService = new LogoutServiceImpl();
    }

    public LogoutServlet(LogoutService logoutService, DataToJson dataToJson) {
        super();
        this.logoutService = logoutService;
        this.dataToJson = dataToJson;
    }

    @Override
    public void init(){
       this.logoutService = new LogoutServiceImpl((AuthenticationImpl) getServletContext().getAttribute("authenticationImpl"));
       this.dataToJson = new DataToJson();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) {

        HttpSession session = request.getSession(false); // request.getSession()
        logoutService.unauthenticate(session.getId());
        session.invalidate();
        dataToJson.processResponse(response, 200,null);
    }
}

