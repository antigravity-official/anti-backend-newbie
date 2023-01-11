package antigravity.web.exception;

import antigravity.infra.exception.ApplicationRuntimeException;
import antigravity.infra.exception.ExceptionMessages;
import org.springframework.http.HttpStatus;

public class InvalidLikeStatusException extends ApplicationRuntimeException {

    public InvalidLikeStatusException() {
        super(ExceptionMessages.INVALID_LIKE_STATUS, HttpStatus.BAD_REQUEST);
    }
}
