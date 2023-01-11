package antigravity.product.errors;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CustomException extends RuntimeException {

	private final ErrorCode errorCode;

	public CustomException(ErrorCode errorCode) {
		this.errorCode = errorCode;
	}

	@Override
	public String getMessage() {
		return errorCode.getMessage();
	}

	public HttpStatus getHttpStatus() {
		return errorCode.getStatus();
	}
}
