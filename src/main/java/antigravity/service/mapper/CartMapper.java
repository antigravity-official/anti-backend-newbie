package antigravity.service.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import antigravity.common.dto.PageResponseDto;
import antigravity.controller.dto.CartResponses;
import antigravity.entity.Cart;
import antigravity.entity.Product;
import antigravity.controller.dto.LikeResponse;

@Component
public class CartMapper {

	public LikeResponse toCartResponse(Long cartId, Long userId, Product product) {
		return LikeResponse.builder()
			.cartId(cartId)
			.userId(userId)
			.sku(product.getSku())
			.name(product.getName())
			.price(product.getPrice())
			.build();
	}

	public PageResponseDto<CartResponses> toPageResponse(Page<Cart> cartPage, List<Long> totalLikes,
		List<Long> views) {
		List<Cart> contents = cartPage.getContent();
		int page = cartPage.getPageable().getPageNumber() + 1;
		int size = cartPage.getPageable().getPageSize();

		List<CartResponses> cartResponses = new ArrayList<>();

		for (int i = 0; i < contents.size(); i++) {
			Cart cart = contents.get(i);
			Long totalLike = totalLikes.get(i);
			Long view = views.get(i);

			cartResponses.add(CartResponses.builder()
				.id(cart.getId())
				.sku(cart.getProduct().getSku())
				.name(cart.getProduct().getName())
				.price(cart.getProduct().getPrice())
				.quantity(cart.getProduct().getQuantity())
				.liked(true)
				.totalLiked(totalLike)
				.viewed(view)
				.createdAt(cart.getCreatedAt())
				.createdAt(cart.getCreatedAt())
				.build());
		}

		return new PageResponseDto<>(page, size, cartResponses);
	}

	public PageResponseDto<CartResponses> toPageResponse(Page<Product> productPage, List<Long> views,
		List<Long> totalLikes, Map<Long, Cart> likedProductIds) {
		List<Product> contents = productPage.getContent();
		int page = productPage.getPageable().getPageNumber() + 1;
		int size = productPage.getPageable().getPageSize();

		List<CartResponses> cartResponses = new ArrayList<>();

		for (int i = 0; i < contents.size(); i++) {
			Product product = contents.get(i);
			Long totalLike = totalLikes.get(i);
			Long view = views.get(i);

			boolean isLike = likedProductIds.containsKey(product.getId());
			Cart cart = likedProductIds.get(product.getId());

			cartResponses.add(CartResponses.builder()
				.id(product.getId())
				.sku(product.getSku())
				.name(product.getName())
				.price(product.getPrice())
				.quantity(product.getQuantity())
				.liked(likedProductIds.containsKey(product.getId()))
				.totalLiked(totalLike)
				.viewed(view)
				.createdAt(
					isLike ? cart.getCreatedAt() : null)
				.createdAt(
					isLike ? cart.getUpdatedAt() : null)
				.build()
			);
		}

		return new PageResponseDto<>(page, size, cartResponses);
	}
}
