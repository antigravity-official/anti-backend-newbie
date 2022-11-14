package antigravity.payload;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
public class ApiResponse<T> {
    private T data;
    private String message;
    private int status;

    private ApiResponse(T data, String message, int status) {
        this.data = data;
        this.message = message;
        this.status = status;
    }

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<T>(data, "SUCCESS", 200);
    }

    public static ApiResponse fail(String message, int status) {
        return new ApiResponse(null, message, status);
    }
}
