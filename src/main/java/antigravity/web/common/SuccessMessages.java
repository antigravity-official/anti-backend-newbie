package antigravity.web.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SuccessMessages implements ResponseMessages {

    PRODUCT_LIKE_SUCCESS("P-T001", "상품을 찜목록에 추가하였습니다."),
    SEARCH_PRODUCTS_SUCCESS("P-T002", "상품목록 조회에 성공하였습니다.");

    private final String code;
    private final String message;
}
