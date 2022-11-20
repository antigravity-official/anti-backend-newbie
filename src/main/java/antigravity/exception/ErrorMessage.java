package antigravity.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorMessage {

    NOT_EXIST_PRODUCT(HttpStatus.BAD_REQUEST, "상품이 존재하지 않습니다."),
    NOT_EXIST_USER(HttpStatus.BAD_REQUEST, "사용자가 존재하지 않습니다."),
    EXIST_WANT_PRODUCT(HttpStatus.BAD_REQUEST, "이미 찜 한 상품입니다.");

    private final String msg;
    private final HttpStatus httpStatus;

    private final int code;

    ErrorMessage(HttpStatus httpStatus, String msg) {
        this.msg = msg;
        this.code = httpStatus.value();
        this.httpStatus = httpStatus;
    }
}
