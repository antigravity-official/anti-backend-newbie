package antigravity.application.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@RequiredArgsConstructor
@Getter
public class ProductResponse {

    private final Long id; // 상품아이디
    private final String sku; // 상품 고유값
    private final String name; // 상품명
    private final BigDecimal price; // 가격
    private final Integer quantity; // 재고수량
    private final Boolean liked; // 필요한 경우 찜한 상품임을 표시 (찜 여부)
    private final Integer totalLiked; // 상품이 받은 모든 찜 개수
    private final Integer viewed; // 상품 조회 수
    private final LocalDateTime createdAt; // 상품 생성일시
    private final LocalDateTime updatedAt; // 상품 수정일시
}
