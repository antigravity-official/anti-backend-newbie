package antigravity.exception;

import antigravity.common.BaseException;

public class IllegalParameterException extends BaseException {
    private final static String MESSAGE = "잘못된 파라미터입니다.";

    public IllegalParameterException() {
        super(MESSAGE);
    }
}
