package api.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiResponseSuccess extends ApiResponse {

    private Object body;

    public ApiResponseSuccess() {
        this.status = "success";
    }

    public ApiResponseSuccess(Object body) {
        this.status = "success";
        this.body = body;
    }
}
