package util.json.deserializer;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

public class IntegerDeserializer implements JsonDeserializer<Integer> {

    public IntegerDeserializer(){

    }

    @Override
    public Integer deserialize(JsonElement element, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        String integerStr = element.getAsString();

        try {
            return Integer.parseInt(integerStr);

        } catch (NumberFormatException e){
            return 0;
        }
    }
}
