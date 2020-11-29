package util.json.deserializer;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import org.apache.log4j.Logger;


import java.lang.reflect.Type;

public class IntegerDeserializer implements JsonDeserializer<Integer> {

    static final Logger LOGGER = Logger.getLogger(IntegerDeserializer.class);

    public IntegerDeserializer(){

    }

    @Override
    public Integer deserialize(JsonElement element, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        String integerStr = element.getAsString();

        if(integerStr.equals("")){
            LOGGER.debug("Empty string for integer value");
            return 0;
        }

        try {
            return Integer.parseInt(integerStr);
        } catch (NumberFormatException e){
            LOGGER.debug(e.getMessage(), e);
            return -1;
        }
    }
}
