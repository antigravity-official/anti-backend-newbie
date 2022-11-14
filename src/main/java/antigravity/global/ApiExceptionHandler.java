package antigravity.global;

import antigravity.exception.*;
import antigravity.payload.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler({UserCommonException.class})
    public ResponseEntity<Object> exceptionHandler(final UserCommonException e) {
        log.error(String.format("[USER EXCEPTION] msg=%s", e));
        return ResponseEntity.badRequest()
                .body(ApiResponse.fail(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
    }

    @ExceptionHandler({ProductLikeCommonException.class})
    public ResponseEntity<Object> exceptionHandler(final ProductLikeCommonException e) {
        log.error(String.format("[PRODUCT-LIKE EXCEPTION] msg=%s", e));
        return ResponseEntity.badRequest()
                .body(ApiResponse.fail(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
    }

    @ExceptionHandler({ProductCommonException.class})
    public ResponseEntity<Object> exceptionHandler(final ProductCommonException e) {
        log.error(String.format("[PRODUCT EXCEPTION] msg=%s", e));
        return ResponseEntity.badRequest()
                .body(ApiResponse.fail(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
    }

    @ExceptionHandler({InvalidHeaderException.class})
    public ResponseEntity<Object> exceptionHandler(final InvalidHeaderException e) {
        log.error(String.format("[INVALID HEADER EXCEPTION] msg=%s", e));
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(ApiResponse.fail(e.getMessage(), HttpStatus.UNAUTHORIZED.value()));
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<Object> exceptionHandler(final Exception e) {
        log.error(e.getMessage(), e);
        return ResponseEntity.internalServerError()
                .body(ApiResponse.fail(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value()));
    }
}

