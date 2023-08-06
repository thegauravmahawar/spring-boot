package imdbapi.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import imdbapi.config.JacksonObjectMapperConfig;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;

public class JsonUtils {

    public static String toJson(Object arg) throws JsonProcessingException {
        return new JacksonObjectMapperConfig().writeValueAsString(arg);
    }

    public static <T> T parseJson(String json, Class<T> clazz) throws IOException {
        if (StringUtils.isBlank(json)) {
            return null;
        }
        return new JacksonObjectMapperConfig().readValue(json, clazz);
    }
}
