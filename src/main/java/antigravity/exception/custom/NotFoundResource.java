package antigravity.exception.custom;

import antigravity.exception.payload.ErrorModel;

public class NotFoundResource extends RuntimeException {
	private final ErrorModel errorModel;

	public NotFoundResource(String message, ErrorModel errorModel) {
		super(message);
		this.errorModel = errorModel;
	}

	public NotFoundResource(String message, Throwable cause, ErrorModel errorModel) {
		super(message, cause);
		this.errorModel = errorModel;
	}

	public ErrorModel getErrorModel() {
		return errorModel;
	}

}
