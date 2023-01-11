package antigravity.common.presentation;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(IllegalStateException.class)
    public ErrorResponse IllegalStateExceptionHandler(IllegalStateException e) {
        log.error("IllegalStateException", e);
        return new ErrorResponse(BAD_REQUEST.toString(), e.getMessage());
    }

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public ErrorResponse IllegalArgumentExceptionHandler(IllegalArgumentException e) {
        log.error("IllegalArgumentException", e);
        return new ErrorResponse(BAD_REQUEST.toString(), e.getMessage());
    }

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(MissingRequestHeaderException.class)
    public ErrorResponse MissingRequestHeaderExceptionHandler(MissingRequestHeaderException e) {
        log.error("MissingRequestHeaderException", e);
        String headerName = e.getHeaderName();
        if (headerName.equals("X-USER-ID")) {
            return new ErrorResponse(BAD_REQUEST.toString(), "인증 정보가 존재하지 않습니다.");
        }
        return new ErrorResponse(BAD_REQUEST.toString(), e.getMessage());
    }

    @ResponseStatus(INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ErrorResponse ExceptionHandler(Exception e) {
        log.error("unknown exception!!", e);
        return new ErrorResponse(INTERNAL_SERVER_ERROR.toString(),
                e.getMessage());
    }
}
