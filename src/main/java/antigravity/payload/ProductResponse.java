package antigravity.payload;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.data.domain.Page;

import antigravity.entity.Product;
import antigravity.entity.User;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ProductResponse {

	private Long id; // 상품아이디
	private String sku; // 상품 고유값
	private String name; // 상품명
	private BigDecimal price; // 가격
	private Integer quantity; // 재고수량
	private Boolean liked; // 필요한 경우 찜한 상품임을 표시 (찜 여부)
	private Integer totalLiked; // 상품이 받은 모든 찜 개수
	private Integer viewed; // 상품 조회 수
	private LocalDateTime createdAt; // 상품 생성일시
	private LocalDateTime updatedAt; // 상품 수정일시

	public static Page<ProductResponse> from(Page<Product> products, User user) {

		return products.map(p -> ProductResponse.builder()
			.id(p.getId())
			.sku(p.getSku())
			.name(p.getName())
			.price(p.getPrice())
			.quantity(p.getQuantity())
			.viewed(p.getViewed())
			.liked(p.getLiked(user))
			.totalLiked(p.getTotalLiked())
			.createdAt(p.getCreatedAt())
			.updatedAt(p.getUpdatedAt())
			.build()
		);
	}
}
