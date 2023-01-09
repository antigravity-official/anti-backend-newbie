package antigravity.exception;

import antigravity.common.BaseException;

public class NotFoundMemberException extends BaseException {
    private final static String MESSAGE = "없는 회원입니다.";

    public NotFoundMemberException() {
        super(MESSAGE);
    }
}
