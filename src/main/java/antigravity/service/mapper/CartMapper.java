package antigravity.service.mapper;

import org.springframework.stereotype.Component;

import antigravity.entity.Product;
import antigravity.payload.CartResponse;

@Component
public class CartMapper {

	public CartResponse toCartResponse(Long cartId, Long userId, Product product, Long viewCount) {
		return CartResponse.builder()
			.cartId(cartId)
			.userId(userId)
			.sku(product.getSku())
			.name(product.getName())
			.price(product.getPrice())
			.totalLike(viewCount)
			.build();
	}
}
