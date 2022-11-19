package antigravity.global.response;

import lombok.Builder;
import lombok.Getter;
import org.springframework.validation.BindingResult;

@Getter
@Builder
public class ApiResponse {

    private final String statusCode;
    private final Object data;

    public ApiResponse(String statusCode, Object data) {
        this.statusCode = statusCode;
        this.data = data;
    }

    public static ApiResponse of(String statusCode, Object data) {
        return ApiResponse.builder()
                .statusCode(statusCode)
                .data(data)
                .build();
    }

    public static ApiResponse of(String statusCode, BindingResult bindingResult) {
        return ApiResponse.builder()
                .statusCode(statusCode)
                .data(bindingResult)
                .build();
    }


}
