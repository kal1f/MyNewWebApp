package servlet;

import service.RegisterService;
import service.impl.RegisterServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "/register")
public class RegisterServlet extends HttpServlet {

    private RegisterService registerService;

    public RegisterServlet() {
        super();
    }

    public RegisterServlet(RegisterService registerService) {
        super();
        this.registerService = registerService;
    }

    @Override
    public void init(){

        registerService = new RegisterServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("register.html").forward(request, response);
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String login = request.getParameter("login");
        String name = request.getParameter("name");
        String password = request.getParameter("password1");

        registerService.createNewCustomerInDb(login, name, password);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(201);
        response.sendRedirect("/login");

    }
}
