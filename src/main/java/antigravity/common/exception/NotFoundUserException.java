package antigravity.common.exception;

public class NotFoundUserException extends AntiGravityBaseException {


    public NotFoundUserException() {
        super(ErrorMessage.NOT_FOUND_USER.getMessage());
    }
}
