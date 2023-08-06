package api.models;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class ServerProps {

    private enum Environment {
        LOCAL, TEST, PROD
    }

    @Value("${application.environment}")
    private String environment;

    public boolean isLocal() {
        return Environment.LOCAL.name().equals(environment);
    }

}
