package utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static utils.ErrorConstants.ERROR_200_MSG;
import static utils.ErrorConstants.ERROR_404_MSG;

public class ResponseHandlerToJson {

    private Gson gson;
    private JsonParser jp;

    public ResponseHandlerToJson() {
        this.gson = new GsonBuilder().setPrettyPrinting().create();
        this.jp = new JsonParser();
    }

    public ResponseHandlerToJson( Gson gson, JsonParser  jp) throws IOException {
        this.gson = gson;
        this.jp = jp;
    }

    public void processResponse(HttpServletResponse response, int statusCode, Object o) {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.setHeader("cache-control", "no-cache");
        response.setStatus(statusCode);
        processResponse(response, o);
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
            String msg;

            if(jsonString == null)
            {
                jsonString = "";
            }
            if(response.getStatus() == 404 && jsonString.equals("")) {

                 msg  = ERROR_404_MSG;
            }
            else if(response.getStatus() == 200){
                 msg = ERROR_200_MSG + jsonString + "}";
            }
            else{
                msg = jsonString;
            }

            JsonElement je = jp.parse(msg);

            String prettyJsonString = gson.toJson(je);

            response.getWriter().write(prettyJsonString);
        }
        catch(Exception e) {
            throw new RuntimeException(Integer.toString(HttpServletResponse.SC_INTERNAL_SERVER_ERROR));
        }
    }

}
