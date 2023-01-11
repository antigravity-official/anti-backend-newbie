package antigravity.application.exception;

import antigravity.infra.exception.ApplicationRuntimeException;
import antigravity.infra.exception.ExceptionMessages;
import org.springframework.http.HttpStatus;

public class ProductNotFoundException extends ApplicationRuntimeException {

    public ProductNotFoundException() {
        super(ExceptionMessages.PRODUCT_NOT_FOUND, HttpStatus.BAD_REQUEST);
    }
}
