package antigravity.service;

import java.text.MessageFormat;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import antigravity.common.dto.PageResponseDto;
import antigravity.controller.dto.CartResponses;
import antigravity.controller.dto.LikeSearchDto;
import antigravity.entity.Cart;
import antigravity.entity.Product;
import antigravity.entity.User;
import antigravity.exception.custom.BusinessException;
import antigravity.exception.custom.NotFoundResource;
import antigravity.exception.payload.ErrorCode;
import antigravity.controller.dto.LikeResponse;
import antigravity.repository.CartRepository;
import antigravity.repository.ProductRepository;
import antigravity.repository.UserRepository;
import antigravity.service.mapper.CartMapper;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class CartService {
	private final CartMapper cartMapper;
	private final ProductCountingService productCountingService;
	private final UserRepository userRepository;
	private final ProductRepository productRepository;
	private final CartRepository cartRepository;

	@Transactional
	public LikeResponse like(Long userId, Long productId) {

		User user = userRepository.findByIdAndDeletedAtIsNull(userId).orElseThrow(
			() -> new NotFoundResource(
				MessageFormat.format("not exist User Id: {0}", userId),
				ErrorCode.NOT_FOUND_RESOURCES
			)
		);

		Product product = productRepository.findByIdAndDeletedAtIsNull(productId).orElseThrow(
			() -> new NotFoundResource(
				MessageFormat.format("not exist product Id: {0}", productId),
				ErrorCode.NOT_FOUND_RESOURCES
			)
		);

		Cart savedCart = null;

		try {
			savedCart = cartRepository.save(Cart.builder()
				.user(user)
				.product(product)
				.build());
		} catch (DataIntegrityViolationException e) {
			throw new BusinessException(
				MessageFormat.format("already want User Id: {0} and product Id: {1}", userId, productId),
				ErrorCode.DUPLICATE_LIKE
			);
		}

		productCountingService.increaseViewCount(productId, userId);
		productCountingService.increaseLike(productId);

		return cartMapper.toCartResponse(savedCart.getId(), userId, product);
	}

	public PageResponseDto<CartResponses> search(Long userId, LikeSearchDto searchDto) {
		User user = userRepository.findByIdAndDeletedAtIsNull(userId).orElseThrow(
			() -> new NotFoundResource(
				MessageFormat.format("not exist User Id: {0}", userId),
				ErrorCode.NOT_FOUND_RESOURCES
			)
		);

		Pageable pageable = searchDto.of();

		if (searchDto.getIsLiked() == null || !searchDto.getIsLiked()) {
			Page<Product> productPage = productRepository.findAllByDeletedAtIsNull(pageable);
			List<Product> products = productPage.getContent();
			Map<Long, Cart> likedProductIds = cartRepository.findByUserAndProductIn(user, products)
				.stream().collect(Collectors.toMap(cart -> cart.getProduct().getId(), cart -> cart));
			List<Long> likes = products.stream().map(product -> productCountingService.findProductLikeCount(product.getId()))
				.collect(Collectors.toList());
			List<Long> views = products.stream().map(product -> productCountingService.findProductViewCount(product.getId()))
				.collect(Collectors.toList());

			return cartMapper.toPageResponse(productPage, views, likes, likedProductIds);
		}

		Page<Cart> cartPage = cartRepository.findByUser(user, pageable);
		List<Product> products = cartPage.stream().map(Cart::getProduct).collect(Collectors.toList());
		List<Long> likes = products.stream().map(product -> productCountingService.findProductLikeCount(product.getId()))
			.collect(Collectors.toList());
		List<Long> views = products.stream().map(product -> productCountingService.findProductViewCount(product.getId()))
			.collect(Collectors.toList());

		return cartMapper.toPageResponse(cartPage, views, likes);

	}
}
