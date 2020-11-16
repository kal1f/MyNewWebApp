package servlet;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;


import service.authentication.AuthenticationImpl;
import service.LoginService;
import utils.ResponseHandlerToJson;


@WebServlet(name = "/login")
public class LoginServlet extends HttpServlet {

    private ResponseHandlerToJson responseHandlerToJson;
    private LoginService loginService;

    public LoginServlet() {
        super();
    }

    public LoginServlet(LoginService loginService) {
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
        this.loginService = new LoginService((AuthenticationImpl) getServletContext().getAttribute("authenticationImpl"));
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

        int status = loginService.toService(session.getId(), login, password);

        if (status == 0) {
            response.setStatus(200);
            response.sendRedirect("/welcome");
        }
        else {
                this.responseHandlerToJson.processResponse(response, 404, null);
            }
        }
    }
