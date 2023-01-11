package antigravity.controller.handler;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum CustomError {

    MEMBER_NOT_FOUND(400,"사용자를 찾을 수 없습니다."),
    PRODUCT_NOT_FOUND(400,"물건을 찾을 수 없습니다"),
    ALERADY_LIKE_PRODUCT(400,"이미 찜한 상품입니다.");






    private final int status;
    private final String message;
}
