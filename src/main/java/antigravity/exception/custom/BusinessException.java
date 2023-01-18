package antigravity.exception.custom;

import antigravity.exception.payload.ErrorModel;

public class BusinessException extends RuntimeException {
	private final ErrorModel errorModel;

	public BusinessException(String message, ErrorModel errorModel) {
		super(message);
		this.errorModel = errorModel;
	}

	public BusinessException(String message, Throwable cause, ErrorModel errorModel) {
		super(message, cause);
		this.errorModel = errorModel;
	}

	public ErrorModel getErrorModel() {
		return errorModel;
	}
}
