package antigravity.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CommonResponse<T> {

    private boolean success;
    private T data;

    public static <T> CommonResponse<T> success(T data) {
        return new CommonResponse<>(true, data);
    }

    public static <T> CommonResponse<T> fail(T data) {
        return new CommonResponse<>(false, data);
    }

    public static <T> CommonResponse<T> create(boolean success, T data) {
        return new CommonResponse<>(success, data);
    }
}