package antigravity.application.exception;


import antigravity.infra.exception.ApplicationRuntimeException;
import antigravity.infra.exception.ExceptionMessages;

public class UserNotFoundException extends ApplicationRuntimeException {

    public UserNotFoundException() {
        super(ExceptionMessages.USER_NOT_FOUND);
    }
}
