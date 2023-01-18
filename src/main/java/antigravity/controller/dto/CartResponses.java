package antigravity.controller.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class CartResponses {
	private Long id;
	private String sku;
	private String name;
	private BigDecimal price;
	private Long quantity;
	private boolean liked;
	private Long totalLiked; // 찜 취소 할 경우도 주는건가?
	private Long viewed; // 조회수
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
}
