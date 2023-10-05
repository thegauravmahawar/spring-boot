package todoapi.exceptions;

import lombok.Getter;

@Getter
public class TodoApiException extends Exception {

    private final String code;

    public TodoApiException(String message, String code) {
        super(message);
        this.code = code;
    }
}
