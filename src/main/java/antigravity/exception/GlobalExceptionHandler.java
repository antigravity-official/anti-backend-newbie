package antigravity.exception;

import javax.validation.ConstraintViolationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import antigravity.exception.custom.BusinessException;
import antigravity.exception.custom.NotFoundParameter;
import antigravity.exception.custom.NotFoundResource;
import antigravity.exception.payload.ErrorCode;
import antigravity.exception.payload.ErrorModel;
import antigravity.exception.payload.ErrorResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {
	private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponse<ErrorModel>> handleMethodArgumentNotValidException(
		MethodArgumentNotValidException e) {
		log.warn("Method argument not valid exception occurred : {}", e.toString(), e);

		return newResponse(ErrorCode.CONSTRAINT_VIOLATION);
	}

	@ExceptionHandler({TransactionSystemException.class, ConstraintViolationException.class})
	public ResponseEntity<ErrorResponse<ErrorModel>> handleConstraintViolation(ConstraintViolationException e) {
		log.warn("Constraint violation exception occurred: {}", e.toString(), e);

		return newResponse(ErrorCode.CONSTRAINT_VIOLATION);
	}

	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<ErrorResponse<ErrorModel>> handleDataIntegrityViolationException(
		DataIntegrityViolationException e) {
		log.warn("DataIntegrityViolation exception occurred : {}", e.toString(), e);

		return newResponse(ErrorCode.DATA_INTEGRITY_VIOLATION);
	}

	@ExceptionHandler(BusinessException.class)
	public ResponseEntity<ErrorResponse<ErrorModel>> handleBusinessException(BusinessException e) {
		log.error("Business exception occurred : {}", e.toString(), e);

		return newResponse(e.getErrorModel());
	}

	@ExceptionHandler(NotFoundResource.class)
	public ResponseEntity<ErrorResponse<ErrorModel>> handleNotFoundException(NotFoundResource e) {
		log.error("NotFoundResource exception occurred : {}", e.toString(), e);

		return newResponse(e.getErrorModel());
	}

	@ExceptionHandler(NotFoundParameter.class)
	public ResponseEntity<ErrorResponse<ErrorModel>> handleNotFoundParameter(NotFoundParameter e) {
		log.error("NotFoundParameter exception occurred : {}", e.toString(), e);

		return newResponse(e.getErrorModel());
	}

	@ExceptionHandler({RuntimeException.class, Exception.class})
	public ResponseEntity<ErrorResponse<ErrorModel>> handleRuntimeException(Throwable e) {
		log.error("Unexpected exception occurred : {}", e.getMessage(), e);

		return newResponse(ErrorCode.FATAL_ERROR);
	}

	private ResponseEntity<ErrorResponse<ErrorModel>> newResponse(ErrorModel errorCode) {
		ErrorResponse<ErrorModel> errorResponse = new ErrorResponse<>(errorCode);
		return new ResponseEntity<>(errorResponse, errorCode.getStatus());
	}
}
