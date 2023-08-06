package imdbapi.exceptions;

import lombok.Getter;

@Getter
public class CustomException extends Exception {

    private final String code;

    public CustomException(String message, String code) {
        super(message);
        this.code = code;
    }

}
