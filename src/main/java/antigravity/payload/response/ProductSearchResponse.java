package antigravity.payload.response;

import antigravity.entity.Product;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class ProductSearchResponse {

    private final Long id;
    private final String sku;
    private final String name;
    private final BigDecimal price;
    private final Integer quantity;
    private final int viewed;
    private final String createdAt;
    private final String updatedAt;
    private final boolean liked;
    private final Integer totalLiked;

    @Builder
    public ProductSearchResponse(Product product, Boolean isLiked, Integer totalLiked
    ) {
        this.id = product.getId();
        this.sku = product.getSku();
        this.name = product.getName();
        this.price = product.getPrice();
        this.quantity = product.getQuantity();
        this.viewed = product.getViewed();
        this.createdAt = product.getCreatedAt().toString();
        this.updatedAt = product.getUpdatedAt().toString();
        this.liked = isLiked;
        this.totalLiked = totalLiked;
    }
}