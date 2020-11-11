package utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import static utils.ErrorConstants.ERROR_200_MSG;
import static utils.ErrorConstants.ERROR_404_MSG;

public class ResponseHandlerToJson {

    private HttpServletResponse response;
    private Gson gson;
    private JsonParser jp;
    private PrintWriter out;

    public ResponseHandlerToJson(HttpServletResponse response) throws IOException {
        this.response = response;
        this.gson = new GsonBuilder().setPrettyPrinting().create();
        this.jp = new JsonParser();
        this.out = response.getWriter();
    }

    public ResponseHandlerToJson(HttpServletResponse response, Gson gson, JsonParser  jp) throws IOException {
        this.response = response;
        this.gson = gson;
        this.jp = jp;
        this.out = response.getWriter();
    }

    public void processResponse(int statusCode, Object o) {

        response.setStatus(statusCode);
        sendPrettyJsonResponse(objectToJson(o));
    }

    public void processResponse(Object o) {
        sendPrettyJsonResponse(objectToJson(o));
    }

    public String objectToJson(Object o) {
        if(o != null) {
            return new Gson().toJson(o);
        }
        else {
            return "";
        }
    }


    public void sendPrettyJsonResponse(String jsonString) {
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

            out.write(prettyJsonString);
        }
        catch(Exception e) {
            throw new RuntimeException(Integer.toString(HttpServletResponse.SC_INTERNAL_SERVER_ERROR));
        }
    }

}
