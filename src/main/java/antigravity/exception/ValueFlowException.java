package antigravity.exception;

import antigravity.common.BaseException;

public class ValueFlowException extends BaseException {
    private final static String MESSAGE = "유효한 범위를 넘어갑니다. 윤영자에게 문의바랍니다.";

    public ValueFlowException() {
        super(MESSAGE);
    }
}

