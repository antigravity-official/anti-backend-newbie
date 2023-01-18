package antigravity.service;

import java.text.MessageFormat;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import antigravity.entity.Cart;
import antigravity.entity.Product;
import antigravity.entity.User;
import antigravity.exception.custom.BusinessException;
import antigravity.exception.custom.NotFoundResource;
import antigravity.exception.payload.ErrorCode;
import antigravity.payload.CartResponse;
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
	private final RedisService redisService;
	private final UserRepository userRepository;
	private final ProductRepository productRepository;
	private final CartRepository cartRepository;

	@Transactional
	public CartResponse like(Long userId, Long productId) {

		User user = userRepository.findByIdAndDeletedAtIsNull(userId).orElseThrow(
			() -> new NotFoundResource(
				MessageFormat.format("not exist User Id: {0}", userId),
				ErrorCode.NOT_FOUND_RESOURCES
			)
		);

		Product product = productRepository.findById(productId).orElseThrow(
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

		Long viewCount = redisService.increaseViewCount(productId,userId);
		Long likeCount = redisService.increaseLike(productId);

		return cartMapper.toCartResponse(savedCart.getId(), userId, product);
	}
}
