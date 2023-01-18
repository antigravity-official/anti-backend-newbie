package antigravity.common.exception;

public class DuplicatedLikeException extends AntiGravityBaseException {


    public DuplicatedLikeException() {
        super(ErrorMessage.DUPLICATE_LIKE.getMessage());
    }
}
