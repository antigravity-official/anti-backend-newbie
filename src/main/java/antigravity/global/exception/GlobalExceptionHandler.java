package antigravity.global.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(AntiException.class)
    public ResponseEntity<ExceptionResponse> handleCheckHouseException(AntiException ex) {
        return ResponseEntity.status(ex.getHttpStatus()).body(ex.getBody());
    }
}
