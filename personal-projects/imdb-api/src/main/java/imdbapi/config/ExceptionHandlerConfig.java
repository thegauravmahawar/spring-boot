package imdbapi.config;

import imdbapi.exceptions.*;
import imdbapi.models.ApiResponse;
import imdbapi.models.ApiResponseError;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerConfig {

    @ExceptionHandler(AlreadyExistException.class)
    public ResponseEntity<ApiResponse> alreadyExistException(AlreadyExistException e) {
        return new ResponseEntity<>(new ApiResponseError(e.getMessage(), e.getCode()), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(InvalidParameterException.class)
    public ResponseEntity<ApiResponse> invalidParameterException(InvalidParameterException e) {
        return new ResponseEntity<>(new ApiResponseError(e.getMessage(), e.getCode()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ApiResponse> notFoundException(NotFoundException e) {
        return new ResponseEntity<>(new ApiResponseError(e.getMessage(), e.getCode()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidHeaderException.class)
    public ResponseEntity<ApiResponse> invalidHeaderException(InvalidHeaderException e) {
        return new ResponseEntity<>(new ApiResponseError(e.getMessage(), e.getCode()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidAuthKeyException.class)
    public ResponseEntity<ApiResponse> invalidAuthKeyException(InvalidAuthKeyException e) {
        return new ResponseEntity<>(new ApiResponseError(e.getMessage(), e.getCode()), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse> unhandledException(Exception e, HttpServletRequest req) {
        e.printStackTrace();
        return new ResponseEntity<>(new ApiResponseError("Something went wrong. Please try again later.", "INTERNAL_SERVER_ERROR"), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
