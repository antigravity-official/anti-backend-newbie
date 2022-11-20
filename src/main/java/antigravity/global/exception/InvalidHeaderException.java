package antigravity.global.exception;

import antigravity.global.common.ErrorCode;
import lombok.Getter;

@Getter
public class InvalidHeaderException extends RuntimeException {

    private final ErrorCode errorCode;

    public InvalidHeaderException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
