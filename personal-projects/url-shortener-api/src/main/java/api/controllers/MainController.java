package api.controllers;

import api.models.ApiResponseSuccess;
import api.models.ServerProps;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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
