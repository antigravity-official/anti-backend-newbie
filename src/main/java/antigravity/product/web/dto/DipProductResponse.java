package antigravity.product.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
public class DipProductResponse {
    private Integer userId;
    private Long productId;
    private Integer viewed;

    @Builder
    public DipProductResponse(Integer userId, Long productId, Integer viewed) {
        this.userId = userId;
        this.productId = productId;
        this.viewed = viewed;
    }
}
