package servlet;

import org.apache.log4j.Logger;
import util.HttpResponseModel;
import util.ResponseHandlerToJson;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet(name = "/welcome")
public class WelcomeServlet extends HttpServlet {

    private HttpResponseModel httpResponseModel;
    private ResponseHandlerToJson responseHandlerToJson;

    static final Logger LOGGER = Logger.getLogger(WelcomeServlet.class);

    public WelcomeServlet() {
        super();
    }
    public WelcomeServlet(HttpResponseModel httpResponseModel, ResponseHandlerToJson responseHandlerToJson) {
        super();
        this.httpResponseModel = httpResponseModel;
        this.responseHandlerToJson = responseHandlerToJson;
    }

    @Override
    public void init(){
        this.responseHandlerToJson = new ResponseHandlerToJson();
        this.httpResponseModel = new HttpResponseModel();

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response){

        responseHandlerToJson.processResponse(response, httpResponseModel);
    }

}
