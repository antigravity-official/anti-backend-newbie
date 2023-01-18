package antigravity.common.exception;

public class NotFoundProductException extends AntiGravityBaseException {

    public NotFoundProductException() {
        super(ErrorMessage.NOT_FOUND_PRODUCT.getMessage());
    }
}
