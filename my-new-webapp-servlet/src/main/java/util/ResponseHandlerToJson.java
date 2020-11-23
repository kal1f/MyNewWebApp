package util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

public class ResponseHandlerToJson {

    private final Gson gson;
    private final JsonParser jp;

    static final Logger LOGGER = Logger.getLogger(ResponseHandlerToJson.class);

    public ResponseHandlerToJson() {
        this.gson = new GsonBuilder().setPrettyPrinting().create();
        this.jp = new JsonParser();
    }

    public ResponseHandlerToJson( Gson gson, JsonParser  jp) {
        this.gson = gson;
        this.jp = jp;
    }

    public void processResponse(HttpServletResponse response, Object o) {

        String objectToJson = objectToJson(o);
        sendPrettyJsonResponse(response, objectToJson);
    }

    public String objectToJson(Object o) {
        if(o != null) {
            return new Gson().toJson(o);
        }
        else {
            return "";
        }
    }

    public void sendPrettyJsonResponse(HttpServletResponse response, String jsonString) {

        try {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");
            response.setHeader("cache-control", "no-cache");

            JsonElement je = jp.parse(jsonString);

            String prettyJsonString = gson.toJson(je);

            response.getWriter().write(prettyJsonString);
        }
        catch(IOException e) {
            LOGGER.debug(e.getMessage(), e);
        }
    }

}
