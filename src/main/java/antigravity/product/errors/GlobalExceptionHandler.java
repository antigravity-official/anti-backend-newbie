package antigravity.product.errors;

import antigravity.product.common.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(CustomException.class)
	public ResponseEntity LikeProductException(CustomException e) {
		return ResponseEntity.status(e.getHttpStatus())
			.body(Response.fail(e.getMessage()));
	}

}
