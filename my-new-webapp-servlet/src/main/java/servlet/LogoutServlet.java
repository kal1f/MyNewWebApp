package servlet;

import org.apache.log4j.Logger;
import service.LogoutService;
import service.authentication.AuthenticationImpl;
import service.impl.LogoutServiceImpl;
import util.HttpResponseModel;
import util.ResponseHandlerToJson;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet(name = "/logout")
public class LogoutServlet extends HttpServlet {

    private LogoutService logoutService;
    private ResponseHandlerToJson responseHandlerToJson;
    private HttpResponseModel httpResponseModel;

    static final Logger LOGGER = Logger.getLogger(LogoutServlet.class);

    public LogoutServlet() {
        super();
        logoutService = new LogoutServiceImpl();
    }

    public LogoutServlet(LogoutService logoutService, ResponseHandlerToJson responseHandlerToJson,
                         HttpResponseModel httpResponseModel) {
        super();
        this.logoutService = logoutService;
        this.responseHandlerToJson = responseHandlerToJson;
        this.httpResponseModel = httpResponseModel;
    }

    @Override
    public void init(){

       this.logoutService = new LogoutServiceImpl((AuthenticationImpl) getServletContext().getAttribute("authenticationImpl"));
       this.httpResponseModel = new HttpResponseModel();
       this.responseHandlerToJson = new ResponseHandlerToJson();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) {

        HttpSession session = request.getSession();
        response.setContentType("application/json");
        logoutService.unauthenticate(session.getId());
        session.invalidate();
        response.setStatus(200);

        httpResponseModel.setStatus(200);
        responseHandlerToJson.processResponse(response, httpResponseModel);

    }
}

