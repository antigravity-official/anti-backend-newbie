package antigravity.global.common;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    NOT_EXISTS_HEADER(HttpStatus.BAD_REQUEST, "Can't find a header info"),
    PRODUCT_NOT_FOUND(HttpStatus.BAD_REQUEST, "Can't find the product"),
    USER_NOT_FOUND(HttpStatus.BAD_REQUEST, "Can't find the user"),
    DUPLICATE_WISH_PRODUCT(HttpStatus.BAD_REQUEST, "It is already in wishlist"),
    INVALID_TYPE(HttpStatus.BAD_REQUEST, "Type deosn't match"),
    ;

    private HttpStatus httpStatus;
    private String message;

    ErrorCode(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }


}
