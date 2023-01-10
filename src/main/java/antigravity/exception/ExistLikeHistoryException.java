package antigravity.exception;

import antigravity.common.BaseException;

public class ExistLikeHistoryException extends BaseException {
    private final static String MESSAGE = "이미 있는 찜목록입니다.";

    public ExistLikeHistoryException() {
        super(MESSAGE);
    }
}
