package imdbapi.controllers;

import imdbapi.models.ApiResponseSuccess;
import imdbapi.models.ServerProps;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

    private final ServerProps serverProps;

    public MainController(ServerProps serverProps) {
        this.serverProps = serverProps;
    }

    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponseSuccess> ok(HttpServletRequest request) {
        return new ResponseEntity<>(new ApiResponseSuccess("ok"), HttpStatus.OK);
    }

    @GetMapping(value = "/env", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponseSuccess> getEnv(HttpServletRequest request) {
        return new ResponseEntity<>(new ApiResponseSuccess(serverProps.getEnvironment()), HttpStatus.OK);
    }
}
