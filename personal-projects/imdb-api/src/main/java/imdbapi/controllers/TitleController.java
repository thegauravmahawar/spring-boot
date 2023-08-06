package imdbapi.controllers;

import imdbapi.dao.Title;
import imdbapi.dao.User;
import imdbapi.exceptions.InvalidParameterException;
import imdbapi.models.ApiResponseSuccess;
import imdbapi.resources.TitleResource;
import imdbapi.services.TitleService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TitleController {

    private final TitleService titleService;

    public TitleController(TitleService titleService) {
        this.titleService = titleService;
    }

    @PostMapping(value = "/title", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponseSuccess createMovie(@RequestBody TitleResource titleResource,  HttpServletRequest request) throws InvalidParameterException {
        User principal = (User) request.getAttribute("principal");
        Title title = titleService.create(titleResource, principal);
        return new ApiResponseSuccess(new TitleResource(title));
    }
}
