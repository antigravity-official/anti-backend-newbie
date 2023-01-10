package antigravity.exception;

import antigravity.common.BaseException;

public class NotFoundProductException extends BaseException {
    private final static String MESSAGE = "없는 회원입니다.";

    public NotFoundProductException() {
        super(MESSAGE);
    }
}
