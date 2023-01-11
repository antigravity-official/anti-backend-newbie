package antigravity.exception.custom;

public class AlreadyLikedException extends AntigravityException {

	private static final String MESSAGE = "이미 찜한 상품입니다.";

	public AlreadyLikedException() {
		super(MESSAGE);
	}

	@Override
	public int getStatusCode() {
		return 400;
	}
}
