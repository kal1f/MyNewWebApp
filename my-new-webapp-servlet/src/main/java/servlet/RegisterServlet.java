package servlet;

import service.RegisterService;
import service.impl.RegisterServiceImpl;
import util.ResponseHandlerToJson;
import util.validator.DataValidator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "/register")
public class RegisterServlet extends HttpServlet {

    private RegisterService registerService;
    private DataValidator dataValidator;
    private ResponseHandlerToJson responseHandlerToJson;


    public RegisterServlet() {
        super();
    }

    public RegisterServlet(RegisterService registerService, DataValidator dataValidator, ResponseHandlerToJson responseHandlerToJson) {
        super();
        this.registerService = registerService;
        this.dataValidator = dataValidator;
        this.responseHandlerToJson = responseHandlerToJson;
    }

    @Override
    public void init(){

        registerService = new RegisterServiceImpl();
        dataValidator = new DataValidator();
        responseHandlerToJson = new ResponseHandlerToJson();

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("register.html").forward(request, response);
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String login = request.getParameter("login");
        String name = request.getParameter("name");
        String password1 = request.getParameter("password1");
        String password2 = request.getParameter("password2");

        if(dataValidator.isRegisterFormValid(login, name, password1, password2)) {
            registerService.createNewCustomerInDb(login, name, password1);

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.setStatus(201);
            response.sendRedirect("/login");
        }
        else{
            //log
            this.responseHandlerToJson.processResponse(response, 400, null);
        }

    }
}
