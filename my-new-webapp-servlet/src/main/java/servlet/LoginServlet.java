package servlet;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;


import service.LoginService;
import service.authentication.AuthenticationImpl;
import service.impl.LoginServiceImpl;
import util.ResponseHandlerToJson;
import util.validator.DataValidator;


@WebServlet(name = "/login")
public class LoginServlet extends HttpServlet {

    private ResponseHandlerToJson responseHandlerToJson;
    private LoginService loginService;

    public LoginServlet() {
        super();
    }

    public LoginServlet(LoginServiceImpl loginService) {
        super();
        this.loginService = loginService;
    }

    public LoginServlet(LoginService loginService, ResponseHandlerToJson responseHandlerToJson) {
        super();
        this.loginService = loginService;
        this.responseHandlerToJson = responseHandlerToJson;
    }

    @Override
    public void init() {
        this.loginService = new LoginServiceImpl((AuthenticationImpl) getServletContext().getAttribute("authenticationImpl"));
        this.responseHandlerToJson = new ResponseHandlerToJson();
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.getRequestDispatcher("login.html").forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String login = request.getParameter("login");
        String password = request.getParameter("password1");
        HttpSession session = request.getSession();
        DataValidator dataValidator = new DataValidator();

        if(dataValidator.isLogInFormValid(login,password)){
            String customer = loginService.returnExistedUserInJson(session.getId(), login, password);

            if (customer != null) {
                response.setStatus(200);
                response.sendRedirect("/welcome");
            }
            else {
                this.responseHandlerToJson.processResponse(response, 404, null);
            }
        }
        else {
            //log System.out.println("Not correct data");
            this.responseHandlerToJson.processResponse(response, 400, null);
        }
    }
}
