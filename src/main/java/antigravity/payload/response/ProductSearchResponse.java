package antigravity.payload.response;

import antigravity.entity.Product;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class ProductSearchResponse extends ProductBaseResponse{

    private final boolean liked;
    private final Integer totalLiked;

    @Builder
    public ProductSearchResponse(Product product, Boolean isLiked, Integer totalLiked
    ) {
        super(product);
        this.liked = isLiked;
        this.totalLiked = totalLiked;
    }
}