package antigravity.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AntigravityException extends RuntimeException {

	private ErrorCode errorCode;
	private String message;

	@Override
	public String getMessage() {
		return String.format("%s. %s", errorCode.getMessage(), message);
	}

}
