package antigravity.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(annotations = RestController.class)
public class ServiceExceptionHandler {

    @ExceptionHandler(NotFoundProductException.class)
    protected ResponseEntity NotFoundProductException(NotFoundProductException e) {
        final ErrorResponse errorResponse = ErrorResponse.builder().code("NOT_FOUND_PRODUCT").message(e.getMessage()).build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(NotFoundUserException.class)
    protected ResponseEntity NotFoundUserException(NotFoundUserException e) {
        final ErrorResponse errorResponse = ErrorResponse.builder().code("NOT_FOUND_USER").message(e.getMessage()).build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(AlreadyBookmarkException.class)
    protected ResponseEntity AlreadyBookmarkException(AlreadyBookmarkException e) {
        final ErrorResponse errorResponse = ErrorResponse.builder().code("Already Bookmark registered").message(e.getMessage()).build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
}

