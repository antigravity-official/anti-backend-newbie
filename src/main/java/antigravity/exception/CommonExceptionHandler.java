package antigravity.exception;

import antigravity.payload.response.CommonResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice // TODO 공부하기
public class CommonExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<CommonResponse<String>> productNotFoundException(ProductNotFoundException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(CommonResponse.fail(e.getMessage()));
    }

    @ExceptionHandler
    public ResponseEntity<CommonResponse<String>> alreadyExistLikedProductException(AlreadyExistLikedProductException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(CommonResponse.fail(e.getMessage()));
    }

    @ExceptionHandler
    public ResponseEntity<CommonResponse<String>> userNotFoundException(UserNotFoundException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(CommonResponse.fail(e.getMessage()));
    }
}
