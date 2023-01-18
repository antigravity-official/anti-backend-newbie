package antigravity.common.exception;

public class RequiredLoginException extends AntiGravityBaseException {

    public RequiredLoginException() {
        super(ErrorMessage.REQUIRED_LOGIN.getMessage());
    }
}
