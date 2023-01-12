package antigravity.payload;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
public class ProductResponse {

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

    @QueryProjection
    @Builder
    public ProductResponse(Long id, String sku, String name, BigDecimal price, Integer quantity, Boolean liked, Integer totalLiked, Integer viewed, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.sku = sku;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.liked = liked;
        this.totalLiked = totalLiked;
        this.viewed = viewed;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
