package antigravity.product.exception;

import antigravity.global.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ProductErrorCode implements ErrorCode {
    PRODUCT_ALREADY_DIP(HttpStatus.BAD_REQUEST, "이미 찜한 상품입니다."),
    PRODUCT_NOT_EXIST(HttpStatus.BAD_REQUEST, "해당 아이디를 가진 상품이 존재하지 않습니다.");
    private final HttpStatus errorCode;
    private final String message;
}
