package antigravity.product.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
public class DipProductResponse {
    private Integer userId;
    private Long productId;
    private Long viewed;

    @Builder
    public DipProductResponse(Integer userId, Long productId, Long viewed) {
        this.userId = userId;
        this.productId = productId;
        this.viewed = viewed;
    }
}
