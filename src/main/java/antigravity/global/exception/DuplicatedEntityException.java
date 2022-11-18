package antigravity.global.exception;

import lombok.Getter;

@Getter
public class DuplicatedEntityException extends RuntimeException {

    private final ErrorCode errorCode;

    public DuplicatedEntityException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
