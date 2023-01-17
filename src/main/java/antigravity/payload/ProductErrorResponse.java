package antigravity.payload;

import antigravity.constant.ErrorCode;
import lombok.*;

@Getter
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductErrorResponse {

    private final Boolean success;
    private final Integer errorCode;
    private final String message;

    public static ProductErrorResponse of (Boolean success, Integer errorCode, String message) {
        return new ProductErrorResponse(success, errorCode, message);
    }

    public static ProductErrorResponse of (Boolean success, ErrorCode errorCode) {
        return new ProductErrorResponse(success, errorCode.getCode(), errorCode.getMessage());
    }

    public static ProductErrorResponse of (Boolean success, ErrorCode errorCode, Exception e) {
        return new ProductErrorResponse(success, errorCode.getCode(), errorCode.getMessage(e));
    }

    public static ProductErrorResponse of (Boolean success, ErrorCode errorCode, String message) {
        return new ProductErrorResponse(success, errorCode.getCode(), errorCode.getMessage(message));
    }
}
