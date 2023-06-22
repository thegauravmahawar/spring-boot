package imdbapi.controllers;

import imdbapi.models.ApiResponseSuccess;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MovieController {

    @PostMapping(value = "/movies", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponseSuccess createMovie(HttpServletRequest request) {
        return new ApiResponseSuccess("Hi");
    }
}
