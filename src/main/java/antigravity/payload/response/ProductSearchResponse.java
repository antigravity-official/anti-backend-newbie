package antigravity.payload.response;

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
    private boolean liked;
    private Integer totalLiked;


    @Builder
    public ProductSearchResponse(Long id, String sku, String name, BigDecimal price,
                                 Integer quantity, int viewed, String createdAt,
                                 String updatedAt, Boolean liked, Integer totalLiked
    ) {
        this.id = id;
        this.sku = sku;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.viewed = viewed;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.liked = liked;
        this.totalLiked = totalLiked;
    }
}