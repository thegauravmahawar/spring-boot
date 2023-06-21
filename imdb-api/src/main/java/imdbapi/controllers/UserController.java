package imdbapi.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import imdbapi.dao.User;
import imdbapi.exceptions.AlreadyExistException;
import imdbapi.exceptions.InvalidParameterException;
import imdbapi.exceptions.NotFoundException;
import imdbapi.models.ApiResponseSuccess;
import imdbapi.resources.UserResource;
import imdbapi.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/users/signup", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponseSuccess signup(@RequestBody UserResource userResource, HttpServletRequest request)
            throws InvalidParameterException, NoSuchPaddingException, IllegalBlockSizeException, UnsupportedEncodingException,
            NoSuchAlgorithmException, BadPaddingException, InvalidKeyException, AlreadyExistException, JsonProcessingException {
        User user = userService.create(userResource);
        return new ApiResponseSuccess(new UserResource(user));
    }

    @PostMapping(value = "/users/auth-key", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ApiResponseSuccess getAuthKey(@RequestBody UserResource userResource, HttpServletRequest request)
            throws InvalidParameterException, NotFoundException, NoSuchPaddingException, IllegalBlockSizeException,
            NoSuchAlgorithmException, BadPaddingException, InvalidKeyException, UnsupportedEncodingException, JsonProcessingException {
        User user = userService.get(userResource);
        return new ApiResponseSuccess(new UserResource(user));
    }
}
