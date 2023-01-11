package antigravity.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
@Setter
public class ProductViewCount {
    private long productId;
    private long viewCount;
}
