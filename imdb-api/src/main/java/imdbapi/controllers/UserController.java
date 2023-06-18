package imdbapi.controllers;

import imdbapi.dao.User;
import imdbapi.models.ApiResponseSuccess;
import imdbapi.resources.UserResource;
import imdbapi.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/signup", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResponseSuccess signup(@RequestBody UserResource userResource, HttpServletRequest request) {
        User user = userService.create(userResource);
        return new ApiResponseSuccess(new UserResource(user));
    }

    @PostMapping(value = "/users/auth-key", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public void getAuthKey(@RequestBody UserResource userResource, HttpServletRequest request) {

    }
}
