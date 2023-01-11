package antigravity.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AntigravityException extends RuntimeException {

	private final ErrorCode errorCode;
	private String message;

	public AntigravityException(ErrorCode errorCode) {
		if (this.message == null) {
			this.message = errorCode.getMessage();
		}

		this.errorCode = errorCode;
	}

	@Override
	public String getMessage() {
		return String.format("%s. %s", this.errorCode.getMessage(), this.message);
	}

}
