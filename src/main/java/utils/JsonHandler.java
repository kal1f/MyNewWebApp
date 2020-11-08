package utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

public class JsonHandler {


    static public void sendResponse(HttpServletResponse response , String jsonString) {
        try {
            String msg = null;
            if(response.getStatus() == 404 && jsonString == null) {

                 msg  = "{\"code\": \"404\", \"error\": \"Unauthorized\", \"message\": \"Customer not found, register please\", \"url\": \"http://localhost:8080/register\"}";
            }
            if(response.getStatus() == 200){
                 msg = "{\"code\": \"200\", \"message\": \"Ok\", " + "\"customers\"" + ": " + jsonString + "}";
            }

            Gson gson = new GsonBuilder().setPrettyPrinting().create();

            JsonParser jp = new JsonParser();

            JsonElement je = jp.parse(msg);

            String prettyJsonString = gson.toJson(je);

            PrintWriter out = response.getWriter();

            out.write(prettyJsonString);
        }
        catch(Exception e) {
            throw new RuntimeException(Integer.toString(HttpServletResponse.SC_INTERNAL_SERVER_ERROR));
        }
    }
}
