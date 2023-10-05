package todoapi.exceptions;

public class InvalidHeaderException extends TodoApiException {

    public InvalidHeaderException(String message, String code) {
        super(message, code);
    }
}
