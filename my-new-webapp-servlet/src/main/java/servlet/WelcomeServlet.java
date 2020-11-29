package servlet;

import binding.response.ErrorResponseBinding;
import org.apache.log4j.Logger;
import util.DataToJson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet(name = "/welcome")
public class WelcomeServlet extends HttpServlet {

    private DataToJson dataToJson;

    static final Logger LOGGER = Logger.getLogger(WelcomeServlet.class);

    public WelcomeServlet() {
        super();
    }
    public WelcomeServlet( DataToJson dataToJson) {
        super();
        this.dataToJson = dataToJson;
    }

    @Override
    public void init(){
        this.dataToJson = new DataToJson();

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response){
        try {
            request.getRequestDispatcher("welcome.html").forward(request, response);
        }catch (IOException | ServletException e){
            LOGGER.error(e.getMessage(), e);
            dataToJson.processResponse(response, 500,
                    ErrorResponseBinding.ERROR_RESPONSE_500);
        }
    }

}
