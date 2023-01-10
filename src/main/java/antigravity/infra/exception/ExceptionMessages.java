package antigravity.infra.exception;

import antigravity.web.payload.common.ResponseMessages;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ExceptionMessages implements ResponseMessages {

    //user
    USER_NOT_FOUND("U-F001", "유저를 찾을 수 없습니다."),
    INVALID_USER_INFO("U-F002", "잘못된 유저 정보입니다."),
    //product
    PRODUCT_NOT_FOUND("P-F001", "상품을 찾을 수 없습니다."),
    ALREADY_LIKED_EXCEPTION("P-F002", "이미 찜한 상품입니다.");

    private final String code;
    private final String message;

}
