package antigravity.payload;

import antigravity.entity.Product;
import antigravity.exception.ValueFlowException;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@JsonInclude(Include.NON_NULL)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    public ProductResponse(Long id, String sku, String name, BigDecimal price, Integer quantity,
                           LocalDateTime createdAt,
                           LocalDateTime updatedAt) {
        this.id = id;
        this.sku = sku;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public ProductResponse(Product product, Long totalLiked, Long viewed) {
        this(product.getId(), product.getSku(), product.getName(), product.getPrice(), product.getQuantity(),
                product.getCreatedAt(), product.getUpdatedAt());
        validateIntegerValue(totalLiked);
        validateIntegerValue(viewed);
        this.totalLiked = totalLiked.intValue();
        this.viewed = viewed.intValue();

    }

    private void validateIntegerValue(long value) {
        if (value >= Integer.MAX_VALUE || value <= Integer.MIN_VALUE) {
            throw new ValueFlowException();
        }
    }

    public void setLiked(Boolean liked) {
        this.liked = liked;
    }
}
