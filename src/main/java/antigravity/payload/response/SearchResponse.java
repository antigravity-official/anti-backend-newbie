package antigravity.payload.response;

import antigravity.entity.Product;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class SearchResponse {

    private Long id; // 상품아이디
    private String sku; // 상품 고유값
    private String name; // 상품명
    private BigDecimal price; // 가격
    private Integer quantity; // 재고수량
    private Boolean liked; // 필요한 경우 찜한 상품임을 표시 (찜 여부)
    private Integer totalLiked; // 상품이 받은 모든 찜 개수
    private Integer viewed; // 상품 조회 수
    private LocalDateTime createdAt; // 상품 생성일시
    private LocalDateTime updatedAt; // 상품 수정일시

    @Builder
    public SearchResponse(Product product, Boolean liked) {
        this.id = product.getId();
        this.sku = product.getSku();
        this.name = product.getName();
        this.price = product.getPrice();
        this.quantity = product.getQuantity();
        this.liked = liked;
        this.totalLiked = product.getTotalLiked();
        this.viewed = product.getViewed();
        this.createdAt = product.getCreatedAt();
        this.updatedAt = product.getUpdatedAt();
    }
}
