package imdbapi.exceptions;

public class InvalidAuthKeyException extends CustomException {
    public InvalidAuthKeyException(String message, String code) {
        super(message, code);
    }
}
