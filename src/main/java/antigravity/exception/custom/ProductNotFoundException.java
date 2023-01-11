package antigravity.exception.custom;

public class ProductNotFoundException extends AntigravityException {

	private static final String MESSAGE = "존재하지 않는 상품입니다.";

	public ProductNotFoundException() {
		super(MESSAGE);
	}

	@Override
	public int getStatusCode() {
		return 400;
	}
}
