package servlet;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import database.entity.Customer;
import org.apache.log4j.Logger;
import service.LoginService;
import service.authentication.AuthenticationImpl;
import service.impl.LoginServiceImpl;
import util.HttpResponseModel;
import util.ResponseHandlerToJson;
import util.validator.DataValidator;


@WebServlet(name = "/login")
public class LoginServlet extends HttpServlet {

    private ResponseHandlerToJson responseHandlerToJson;
    private LoginService loginService;
    private HttpResponseModel httpResponseModel;
    private DataValidator dataValidator;

    static final Logger LOGGER = Logger.getLogger(LoginServlet.class);

    public LoginServlet() {
        super();
    }

    public LoginServlet(LoginServiceImpl loginService) {
        super();
        this.loginService = loginService;
    }

    public LoginServlet(LoginService loginService, ResponseHandlerToJson responseHandlerToJson,
                        HttpResponseModel httpResponseModel, DataValidator dataValidator) {
        super();
        this.loginService = loginService;
        this.responseHandlerToJson = responseHandlerToJson;
        this.httpResponseModel = httpResponseModel;
        this.dataValidator = dataValidator;
    }

    @Override
    public void init() {
        this.loginService = new LoginServiceImpl((AuthenticationImpl) getServletContext().getAttribute("authenticationImpl"));
        this.responseHandlerToJson = new ResponseHandlerToJson();
        this.dataValidator = new DataValidator();
        this.httpResponseModel = new HttpResponseModel();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        try {
            request.getRequestDispatcher("login.html").forward(request, response);
        }catch (IOException | ServletException e){
            LOGGER.error(e.getMessage(), e);
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {

        String login = request.getParameter("login");
        String password = request.getParameter("password1");
        HttpSession session = request.getSession();

        if(dataValidator.isLogInFormValid(login,password)){

            Customer customer = loginService.authenticate(session.getId(), login, password);

            LOGGER.debug("Check customer: "+ customer);
            if(customer != null) {
                LOGGER.debug("{} "+customer);

                response.setStatus(200);
                httpResponseModel.setStatus(200);
                httpResponseModel.setMessage("Ok");
                httpResponseModel.setCustomer(customer);
                responseHandlerToJson.processResponse(response, httpResponseModel);
            }
            else {
                LOGGER.debug("loginService.returnExistedUserInJson() returned null value.");

                httpResponseModel.setStatus(404);
                httpResponseModel.setError("Unauthorized");
                responseHandlerToJson.processResponse(response, httpResponseModel);

            }
        }
        else {
            LOGGER.debug("Login: "+login+" or "+" password: "+password+"is not valid.");

            httpResponseModel.setStatus(400);
            httpResponseModel.setMessage("Login: or password: is not valid.");
            responseHandlerToJson.processResponse(response, httpResponseModel);

        }
    }
}
