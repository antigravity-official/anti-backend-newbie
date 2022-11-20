package antigravity.exception;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;
import java.util.NoSuchElementException;
import java.util.concurrent.RejectedExecutionException;


@Slf4j
@RestControllerAdvice
public class RestApiExceptionHandler {

    @ExceptionHandler(value = { IllegalArgumentException.class })
    public ResponseEntity<RestApiException> handleApiRequestException(IllegalArgumentException ex) {
        String message = getExceptionMessage(ex.getMessage());

        //에러 메세지만 잡아내기위함
        StackTraceElement[] stackTraceElements = ex.getStackTrace();
        log.error(message,stackTraceElements[0]);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(RestApiException.builder()
                .resultFlag(false)
                .code(HttpStatus.BAD_REQUEST.value())
                .errorMessage(ex.getMessage())
                .build());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<RestApiException> handleConstraintViolation(ConstraintViolationException ex) {

        String message = getExceptionMessage(ex.getMessage());

        //에러 메세지만 잡아내기위함
        StackTraceElement[] stackTraceElements = ex.getStackTrace();
        log.error(message,stackTraceElements[0]);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(RestApiException.builder()
                .resultFlag(false)
                .code(HttpStatus.BAD_REQUEST.value())
                .errorMessage(ex.getMessage())
                .build());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<RestApiException> handleValidationExceptions(MethodArgumentNotValidException ex) {
        String message = getExceptionMessage(ex.getMessage());

        //에러 메세지만 잡아내기위함
        StackTraceElement[] stackTraceElements = ex.getStackTrace();
        log.error(message,stackTraceElements[0]);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(RestApiException.builder()
                .resultFlag(false)
                .code(HttpStatus.BAD_REQUEST.value())
                .errorMessage(ex.getMessage())
                .build());
    }

    @ExceptionHandler(RejectedExecutionException.class)
    public ResponseEntity<RestApiException> handleValidationExceptions(RejectedExecutionException ex) {
        String message = getExceptionMessage(ex.getMessage());

        //에러 메세지만 잡아내기위함
        StackTraceElement[] stackTraceElements = ex.getStackTrace();
        log.error(message,stackTraceElements[0]);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(RestApiException.builder()
                .resultFlag(false)
                .code(HttpStatus.BAD_REQUEST.value())
                .errorMessage(ex.getMessage())
                .build());
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<RestApiException> handleValidationExceptions(NoSuchElementException ex) {
        String message = getExceptionMessage(ex.getMessage());

        //에러 메세지만 잡아내기위함
        StackTraceElement[] stackTraceElements = ex.getStackTrace();
        log.error(message,stackTraceElements[0]);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(RestApiException.builder()
                .resultFlag(false)
                .code(HttpStatus.BAD_REQUEST.value())
                .errorMessage(ex.getMessage())
                .build());
    }
    private String getExceptionMessage(String message){
        if(StringUtils.hasText(message)){
            return message + "\n \t {}";
        }
        return "\n \t {}";
    }
}
