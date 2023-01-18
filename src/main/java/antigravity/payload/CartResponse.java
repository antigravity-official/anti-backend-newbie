package antigravity.payload;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CartResponse {
	private Long cartId;
	private Long userId;
	private String sku; // 상품 고유값
	private String name;
	private BigDecimal price;
	private Long totalLike;

}
