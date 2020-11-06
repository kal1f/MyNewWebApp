package servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import database.CustomerDAO;
import database.CustomerDAOImpl;
import database.entity.Customer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;


@WebServlet(name = "/customers")
public class CustomerServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private CustomerDAO cd;

    @Override
    public void init(){
        cd = new CustomerDAOImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String login = request.getParameter("login");
        String id = request.getParameter("id");

        ArrayList<Customer> c;

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        if (login != null || id != null) {

            c = cd.getCustomerByIdOrLogin(login, id);

        } else {

            c = cd.getCustomers();

        }
        final String jsonString = new Gson().toJson(c);

        response.setStatus(200);

        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        JsonParser jp = new JsonParser();

        JsonElement je = jp.parse(jsonString);

        String prettyJsonString = gson.toJson(je);

        PrintWriter out = response.getWriter();

        out.write(prettyJsonString);

    }

}
