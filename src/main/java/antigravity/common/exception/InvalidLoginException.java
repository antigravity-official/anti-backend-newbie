package antigravity.common.exception;

public class InvalidLoginException extends AntiGravityBaseException {

    public InvalidLoginException() {
        super(ErrorMessage.INVALID_USER_INFO.getMessage());
    }
}
