package todoapi.exceptions;

public class InvalidParameterException extends TodoApiException {

    public InvalidParameterException(String message, String code) {
        super(message, code);
    }
}
