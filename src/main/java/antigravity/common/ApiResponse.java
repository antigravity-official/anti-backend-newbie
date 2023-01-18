package antigravity.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ApiResponse<T> {

    private static final String SUCCESS_MESSAGE = "OK";

    private String message;
    private T data;

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(SUCCESS_MESSAGE, data);
    }

    public static ApiResponse<Void> error(String message) {
        return new ApiResponse<>(message, null);
    }

}
