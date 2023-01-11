package antigravity.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import antigravity.exception.custom.AntigravityException;
import antigravity.payload.ErrorResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class ExceptionController {

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ErrorResponse invalidRequestHandler(MethodArgumentNotValidException e) {
		ErrorResponse response = ErrorResponse.builder()
				.code("400")
				.message("잘못된 요청입니다.")
				.build();

		for (FieldError fieldError : e.getFieldErrors()) {
			response.addValidation(fieldError.getField(), fieldError.getDefaultMessage());
		}
		return response;
	}

	@ExceptionHandler(AntigravityException.class)
	public ResponseEntity<ErrorResponse> antigravityException(AntigravityException e) {
		int statusCode = e.getStatusCode();

		ErrorResponse body = ErrorResponse.builder()
				.code(String.valueOf(statusCode))
				.validation(e.getValidation())
				.build();

		return ResponseEntity.status(statusCode).body(body);
	}
}
