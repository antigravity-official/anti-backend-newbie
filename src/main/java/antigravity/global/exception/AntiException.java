package antigravity.global.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class AntiException extends RuntimeException{
    private final HttpStatus httpStatus;
    private final ExceptionResponse body;
    public AntiException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.httpStatus = errorCode.getErrorCode();
        this.body = new ExceptionResponse(errorCode.getMessage(), errorCode.getErrorCode());
    }
    public AntiException(HttpStatus httpStatus, ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.httpStatus = httpStatus;
        this.body = new ExceptionResponse(errorCode.getMessage(), errorCode.getErrorCode());
    }
}
