package antigravity.product.repository;

import antigravity.product.domain.Product;
import antigravity.product.domain.ProductLike;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

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

    @Builder
    private ProductResponse(Long id, String sku, String name, BigDecimal price, Integer quantity,
            Boolean liked, Integer totalLiked, Integer viewed, LocalDateTime createdAt,
            LocalDateTime updatedAt) {
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

    public static ProductResponse of(Long userId, Product product, List<ProductLike> productLikes, Integer totalViews) {
        Long productId = product.getId();
        return ProductResponse.builder()
                .id(productId)
                .sku(product.getSku())
                .name(product.getName())
                .price(product.getPrice())
                .quantity(product.getQuantity())
                .liked(checkLikedProduct(userId, productLikes))
                .totalLiked(productLikes.size())
                .viewed(totalViews)
                .createdAt(product.getCreatedAt())
                .updatedAt(product.getUpdatedAt())
                .build();
    }

    private static Boolean checkLikedProduct(Long userId, List<ProductLike> productLikes) {
        return productLikes.stream().anyMatch(pl -> pl.getUserId().equals(userId));
    }

}
