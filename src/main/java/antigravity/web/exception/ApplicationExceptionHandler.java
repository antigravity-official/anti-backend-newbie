package antigravity.web.exception;

import antigravity.infra.exception.ApplicationRuntimeException;
import antigravity.web.payload.common.ApplicationResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApplicationExceptionHandler {

    @ExceptionHandler(value = ApplicationRuntimeException.class)
    public ApplicationResponseEntity<Void> handleException(ApplicationRuntimeException e) {
        return new ApplicationResponseEntity<>(e.getExceptionMessages(), e.getHttpStatus());
    }
}
