package antigravity.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import antigravity.payload.Response;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(AntigravityException.class)
	public ResponseEntity<?> apiException(AntigravityException e) {
		return ResponseEntity.status(e.getErrorCode().getStatus())
			.body(Response.error(e.getErrorCode().name()));
	}

	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<?> apiException(RuntimeException e) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
			.body(Response.error(ErrorCode.BAD_REQUEST.name()));
	}

}
