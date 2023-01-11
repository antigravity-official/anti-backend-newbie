package antigravity.infra.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ApplicationRuntimeException extends RuntimeException {

    private ExceptionMessages exceptionMessages;
    private HttpStatus httpStatus;

    public ApplicationRuntimeException(ExceptionMessages exceptionMessages, HttpStatus httpStatus) {
        super(exceptionMessages.getMessage());
        this.exceptionMessages = exceptionMessages;
        this.httpStatus = httpStatus;
    }

}
