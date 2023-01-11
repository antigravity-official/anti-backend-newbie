package antigravity.product.domain.entity;


import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class LikeProduct {
    private Long id;
    private Integer userId;
    private Long productId;

}
