package antigravity.payload;

import antigravity.entity.Product;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@RequiredArgsConstructor
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

    public ProductResponse(Product product, Long memberId) {
        this.id = product.getId();
        this.name = product.getName();
        this.sku = product.getSku();
        this.price = product.getPrice();
        this.quantity = product.getQuantity();
        // 찜을 눌렀으면 표기
        this.liked = product.memberMatchLikedStatus(memberId).liked();
        this.totalLiked = product.totalLiked();
        this.viewed = product.getViewList().size();
        this.createdAt = product.getCreatedAt();
        this.updatedAt = product.getUpdatedAt();
    }
}
