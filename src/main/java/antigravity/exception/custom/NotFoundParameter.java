package antigravity.exception.custom;

import antigravity.exception.payload.ErrorModel;

public class NotFoundParameter extends RuntimeException{
	private final ErrorModel errorModel;

	public NotFoundParameter(String message, ErrorModel errorModel) {
		super(message);
		this.errorModel = errorModel;
	}

	public NotFoundParameter(String message, Throwable cause, ErrorModel errorModel) {
		super(message, cause);
		this.errorModel = errorModel;
	}

	public ErrorModel getErrorModel() {
		return errorModel;
	}
}
