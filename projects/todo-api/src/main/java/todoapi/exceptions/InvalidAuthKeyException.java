package todoapi.exceptions;

public class InvalidAuthKeyException extends TodoApiException {

    public InvalidAuthKeyException(String message, String code) {
        super(message, code);
    }
}
