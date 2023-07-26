package imdbapi.controllers;

import imdbapi.dao.User;
import imdbapi.dao.UserList;
import imdbapi.exceptions.InvalidParameterException;
import imdbapi.exceptions.NotFoundException;
import imdbapi.models.ApiResponseSuccess;
import imdbapi.resources.UserListResource;
import imdbapi.services.UserListService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserListController {

    private final UserListService userListService;

    public UserListController(UserListService userListService) {
        this.userListService = userListService;
    }

    @PostMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponseSuccess createUserList(@RequestBody UserListResource userListResource,
                                             HttpServletRequest request) throws InvalidParameterException {
        User principal = (User) request.getAttribute("principal");
        UserList userList = userListService.create(userListResource, principal);
        return new ApiResponseSuccess(new UserListResource(userList));
    }

    @PostMapping(value = "/list/titles", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponseSuccess addTitlesToUserList(@RequestBody UserListResource userListResource,
                                                  HttpServletRequest request) throws InvalidParameterException {
        User principal = (User) request.getAttribute("principal");
        UserList userList = userListService.addTitles(userListResource, principal);
        return new ApiResponseSuccess(new UserListResource(userList));
    }
}
