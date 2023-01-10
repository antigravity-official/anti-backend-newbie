package antigravity.application.exception;

import antigravity.infra.exception.ApplicationRuntimeException;
import antigravity.infra.exception.ExceptionMessages;

public class ProductNotFoundException extends ApplicationRuntimeException {

    public ProductNotFoundException() {
        super(ExceptionMessages.PRODUCT_NOT_FOUND);
    }
}
