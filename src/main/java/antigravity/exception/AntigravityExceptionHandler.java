package antigravity.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.concurrent.ExecutionException;

import static antigravity.exception.ErrorCode.*;

@RestControllerAdvice
public class AntigravityExceptionHandler {
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponse> illegalArgumentException(CustomException exception) {

        ErrorCode errorCode = exception.getErrorCode();

        ErrorResponse response = ErrorResponse.builder()
                .status(errorCode.getStatus())
                .message(errorCode.getMessage())
                .errorCode(errorCode.getCode())
                .build();

        ResponseEntity responseEntity = new ResponseEntity(response, HttpStatus.resolve(exception.getErrorCode().getStatus()));
        return new ResponseEntity(response, HttpStatus.resolve(exception.getErrorCode().getStatus()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> illegalArgumentException(ExecutionException e) {
        ErrorResponse response = ErrorResponse.builder()
                .status(INTERNAL_SERVER_ERROR.getStatus())
                .message(e.getMessage())
                .errorCode(INTERNAL_SERVER_ERROR.getCode())
                .build();

        return new ResponseEntity(response, HttpStatus.resolve(INTERNAL_SERVER_ERROR.getStatus()));
    }
}
