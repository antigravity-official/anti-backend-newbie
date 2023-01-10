package antigravity.application.exception;

import antigravity.infra.exception.ApplicationRuntimeException;
import antigravity.infra.exception.ExceptionMessages;
import org.springframework.http.HttpStatus;

public class AlreadyLikedException extends ApplicationRuntimeException {

    public AlreadyLikedException() {
        super(ExceptionMessages.ALREADY_LIKED_EXCEPTION, HttpStatus.BAD_REQUEST);
    }

}
