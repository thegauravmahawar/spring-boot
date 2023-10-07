package todoapi.controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import todoapi.models.ApiResponseSuccess;
import todoapi.models.ServerProps;

@RestController
public class MainController {

    private final ServerProps serverProps;

    public MainController(ServerProps serverProps) {
        this.serverProps = serverProps;
    }

    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResponseSuccess ok(HttpServletRequest request) {
        return new ApiResponseSuccess("ok");
    }

    @GetMapping(value = "/env", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResponseSuccess getEnv(HttpServletRequest request) {
        return new ApiResponseSuccess(serverProps.getEnvironment());
    }
}
