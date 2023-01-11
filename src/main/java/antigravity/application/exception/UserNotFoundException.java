package antigravity.application.exception;


import antigravity.infra.exception.ApplicationRuntimeException;
import antigravity.infra.exception.ExceptionMessages;
import org.springframework.http.HttpStatus;

public class UserNotFoundException extends ApplicationRuntimeException {

    public UserNotFoundException() {
        super(ExceptionMessages.USER_NOT_FOUND, HttpStatus.BAD_REQUEST);
    }
}
