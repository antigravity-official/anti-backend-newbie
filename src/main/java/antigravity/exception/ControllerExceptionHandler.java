package antigravity.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<CMResDto<?>> customException(CustomException e) {
        return new ResponseEntity<>(new CMResDto<>("400 BAD_REQUEST",e.getMessage(),null), HttpStatus.BAD_REQUEST);
    }
}
