package antigravity.exception.payload;

import java.time.LocalDateTime;

public class ErrorResponse <T extends ErrorModel>{
	private final String code;
	private final String message;
	private final LocalDateTime dateTime;

	public ErrorResponse(T errorCode) {
		this.code = errorCode.getCode();
		this.message = errorCode.getMessage();
		this.dateTime = LocalDateTime.now();
	}

	public String getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

	public LocalDateTime getDateTime() {
		return dateTime;
	}
}
