package imdbapi.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JSR310Module;
import org.springframework.stereotype.Component;

@Component
public class JacksonObjectMapperConfig extends ObjectMapper {

    public JacksonObjectMapperConfig() {
        ObjectMapper objectMapper = registerModule(new JSR310Module());
        objectMapper.configure(MapperFeature.DEFAULT_VIEW_INCLUSION, true);
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    }

}
