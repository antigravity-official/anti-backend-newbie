package antigravity.web.exception;

import antigravity.infra.exception.ApplicationRuntimeException;
import antigravity.infra.exception.ExceptionMessages;
import org.springframework.http.HttpStatus;

public class InvalidUserInfoException extends ApplicationRuntimeException {

    public InvalidUserInfoException() {
        super(ExceptionMessages.INVALID_USER_INFO, HttpStatus.BAD_REQUEST);
    }
}
