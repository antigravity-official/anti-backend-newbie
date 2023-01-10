package antigravity.web.exception;

import antigravity.infra.exception.ApplicationRuntimeException;
import antigravity.infra.exception.ExceptionMessages;

public class InvalidUserInfoException extends ApplicationRuntimeException {

    public InvalidUserInfoException() {
        super(ExceptionMessages.INVALID_USER_INFO);
    }
}
