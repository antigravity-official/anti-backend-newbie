package antigravity.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    NOT_FOUND_USER(HttpStatus.BAD_REQUEST, "해당 유저를 찾을 수 없습니다."),
    NOT_FOUND_PRODUCT(HttpStatus.BAD_REQUEST, "해당 상품을 찾을 수 없습니다."),
    CAN_NOT_LIKED(HttpStatus.BAD_REQUEST, "해당 상품은 이미 찜을 하셨습니다."),
    NOT_EXISTS_USER(HttpStatus.BAD_REQUEST, "해당 계정은 삭제된 계정입니다."),
    NOT_EXISTS_PRODUCT(HttpStatus.BAD_REQUEST, "해당 상품은 삭제된 상품입니다.");

    private final HttpStatus httpStatus;
    private final String message;
}