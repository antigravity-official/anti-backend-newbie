package antigravity.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public class ExceptionResponse {
    private final String message;
    private final HttpStatus code;

    public static ExceptionResponse of(GlobalErrorCode errorCode) {
        return new ExceptionResponse(errorCode.getMessage(), errorCode.getErrorCode());
    }
}
