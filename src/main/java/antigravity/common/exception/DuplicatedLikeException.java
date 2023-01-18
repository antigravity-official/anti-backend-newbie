package antigravity.common.exception;

public class DuplicatedLikeException extends AntiGravityBaseException {

    private static final String ERROR_MESSAGE = "이미 찜한 상품입니다.";

    public DuplicatedLikeException() {
        super(ERROR_MESSAGE);
    }
}
