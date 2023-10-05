package todoapi.exceptions;

public class NotFoundException extends TodoApiException {

    public NotFoundException(String message, String code) {
        super(message, code);
    }
}
