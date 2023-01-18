package antigravity.controller;

import antigravity.common.ApiResponse;
import antigravity.common.exception.AntiGravityBaseException;
import antigravity.common.exception.ErrorMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(AntiGravityBaseException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse<Void> handleAntiGravityException(AntiGravityBaseException exception) {
        return ApiResponse.error(exception.getMessage());
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse<Void> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException exception) {
        log.debug(exception.getMessage());
        return ApiResponse.error(ErrorMessage.INVALID_QUERY_PARAM.getMessage());
    }
}
