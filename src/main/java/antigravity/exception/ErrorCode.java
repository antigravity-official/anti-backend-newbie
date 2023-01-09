package antigravity.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    // USER 관련 Exception
    USER_NOT_FOUND(HttpStatus.BAD_REQUEST, "해당 유저를 찾지 못했습니다."),

    // PRODUCT 관련 Exception
    PRODUCT_NOT_FOUND(HttpStatus.BAD_REQUEST, "해당 상품을 찾지 못했습니다."),

    // PRODUCT-LIKE 관련 Exception
    CAN_NOT_LIKE(HttpStatus.BAD_REQUEST, "찜을 하실 수 없습니다.");

    private final HttpStatus status;

    private final String message;
}