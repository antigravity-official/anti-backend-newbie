package antigravity.exception;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.NoSuchElementException;
import java.util.concurrent.RejectedExecutionException;


@Slf4j
@RestControllerAdvice
public class RestApiExceptionHandler {
    @ExceptionHandler(RejectedExecutionException.class)
    public ResponseEntity<ResponseErrorMsg> handleValidationExceptions(RejectedExecutionException ex) {
        String message = getExceptionMessage(ex.getMessage());

        StackTraceElement[] stackTraceElements = ex.getStackTrace();
        log.error(message,stackTraceElements[0]);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseErrorMsg(ex.getMessage(),HttpStatus.BAD_REQUEST.value()));
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ResponseErrorMsg> handleValidationExceptions(NoSuchElementException ex) {
        String message = getExceptionMessage(ex.getMessage());

        StackTraceElement[] stackTraceElements = ex.getStackTrace();
        log.error(message,stackTraceElements[0]); // 0번(에러메세지만) 저장

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseErrorMsg(ex.getMessage(),HttpStatus.BAD_REQUEST.value()));
    }
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ResponseErrorMsg> handleValidationExceptions(IllegalArgumentException ex) {
        String message = getExceptionMessage(ex.getMessage());

        StackTraceElement[] stackTraceElements = ex.getStackTrace();
        log.error(message,stackTraceElements[0]); // 0번(에러메세지만) 저장

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseErrorMsg(ex.getMessage(),HttpStatus.BAD_REQUEST.value()));
    }

    private String getExceptionMessage(String message){
        if(StringUtils.hasText(message)){
            return message + "\n \t {}";
        }
        return "\n \t {}";
    }
}
