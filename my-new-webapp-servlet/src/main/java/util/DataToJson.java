package util;

import binding.response.ResponseBinding;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

public class DataToJson {

    private final Gson gson;
    private final JsonParser jp;


    static final Logger LOGGER = Logger.getLogger(DataToJson.class);

    public DataToJson() {
        this.gson = new GsonBuilder().setPrettyPrinting().create();
        this.jp = new JsonParser();
    }

    public DataToJson(Gson gson, JsonParser jp) {
        this.gson = gson;
        this.jp = jp;
    }

    public void processResponse(HttpServletResponse response, int statusCode, ResponseBinding model) {
        response.setStatus(statusCode);
        processResponse(response, model);
    }

    public void processResponse(HttpServletResponse response, ResponseBinding model) {

        String objectToJson = objectToJson(model);
        sendPrettyJsonResponse(response, objectToJson);
    }

    public String objectToJson(ResponseBinding model) {

        if (model != null) {
            return new Gson().toJson(model);
        } else {
            return "";
        }
    }

    public void sendPrettyJsonResponse(HttpServletResponse response, String jsonString) {

        try {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");
            response.setHeader("cache-control", "no-cache");

            response.getWriter().write(jsonString);
        } catch (IOException e) {
            LOGGER.debug(e.getMessage(), e);
        }
    }

}
