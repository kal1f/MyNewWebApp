package servlet;

import org.apache.log4j.Logger;
import service.RegisterService;
import service.impl.RegisterServiceImpl;
import util.HttpResponseModel;
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
    private HttpResponseModel httpResponseModel;
    private ResponseHandlerToJson responseHandlerToJson;

    static final Logger LOGGER = Logger.getLogger(RegisterServlet.class);


    public RegisterServlet() {
        super();
    }

    public RegisterServlet(RegisterService registerService, ResponseHandlerToJson responseHandlerToJson,
                           DataValidator dataValidator, HttpResponseModel httpResponseModel) {
        super();
        this.registerService = registerService;
        this.dataValidator = dataValidator;
        this.responseHandlerToJson = responseHandlerToJson;
        this.httpResponseModel = httpResponseModel;
    }

    @Override
    public void init(){

        registerService = new RegisterServiceImpl();
        dataValidator = new DataValidator();
        responseHandlerToJson = new ResponseHandlerToJson();
        httpResponseModel = new HttpResponseModel();

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        try {
            request.getRequestDispatcher("register.html").forward(request, response);
        }catch (ServletException | IOException e){
            LOGGER.error(e.getMessage(), e);
        }

    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        String login = request.getParameter("login");
        String name = request.getParameter("name");
        String password1 = request.getParameter("password1");
        String password2 = request.getParameter("password2");

        if(dataValidator.isRegisterFormValid(login, name, password1, password2)) {
            registerService.createNewCustomerInDb(login, name, password1);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.setStatus(200);

            httpResponseModel.setStatus(200);
            httpResponseModel.setMessage("Ok");

        }
        else{
            LOGGER.info("Customer with login:"+login+" name: "+name+" password1 "+password1+" password2 "+password2+" can not be registered.");

            httpResponseModel.setStatus(400);
            httpResponseModel.setMessage("Customer with this params can't be registered.");

        }
        responseHandlerToJson.processResponse(response, httpResponseModel);

    }
}
