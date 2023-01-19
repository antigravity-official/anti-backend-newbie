package antigravity.controller.dto;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class LikeResponse {
	private Long cartId;
	private Long userId;
	private String sku;
	private String name;
	private BigDecimal price;
}
