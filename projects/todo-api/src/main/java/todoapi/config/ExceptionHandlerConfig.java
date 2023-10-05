package todoapi.config;

import todoapi.exceptions.*;
import todoapi.models.ApiResponse;
import todoapi.models.ApiResponseError;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import todoapi.utils.MessageUtils;

@ControllerAdvice
public class ExceptionHandlerConfig {

    private final MessageUtils messageUtils;

    public ExceptionHandlerConfig(MessageUtils messageUtils) {
        this.messageUtils = messageUtils;
    }

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
        return new ResponseEntity<>(
                new ApiResponseError(
                        messageUtils.getMessage("error.somethingWentWrong"),
                        messageUtils.getMessage("error.code.somethingWentWrong")
                ), HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
}

