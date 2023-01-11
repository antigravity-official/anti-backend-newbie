package antigravity.product.web.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class LikeProductResponse {
    private Integer userId;
    private Long productId;
    private Long viewed;

    @Builder
    public LikeProductResponse(Integer userId, Long productId, Long viewed) {
        this.userId = userId;
        this.productId = productId;
        this.viewed = viewed;
    }
}
