package utils;

import com.google.gson.Gson;
import com.google.gson.JsonElement;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;

public class JsonHandler {

    static public void sendResponse(HttpServletResponse response , String payload) {
        try {

            Gson gson = new Gson();
            JsonElement json = gson.fromJson(payload, JsonElement.class);

            OutputStream out = response.getOutputStream();
            out.write(payload.getBytes());
            out.flush();
        }
        catch(Exception e) {
            throw new RuntimeException(Integer.toString(HttpServletResponse.SC_INTERNAL_SERVER_ERROR));
        }
    }
}
