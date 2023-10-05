package todoapi.exceptions;

public class AlreadyExistException extends TodoApiException {

    public AlreadyExistException(String message, String code) {
        super(message, code);
    }
}
