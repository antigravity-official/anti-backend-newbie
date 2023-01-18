package antigravity.controller;

import antigravity.common.ApiResponse;
import antigravity.common.exception.AntiGravityBaseException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AntiGravityBaseException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse<Void> handleAntiGravityException(AntiGravityBaseException exception) {
        return ApiResponse.error(exception.getMessage());
    }

}
