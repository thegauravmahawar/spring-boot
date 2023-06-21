package imdbapi.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import imdbapi.config.JacksonObjectMapperConfig;

public class JsonUtils {

    public static String toJson(Object arg) throws JsonProcessingException {
        return new JacksonObjectMapperConfig().writeValueAsString(arg);
    }
}
