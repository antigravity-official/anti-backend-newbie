package antigravity.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import antigravity.entity.Product;
import antigravity.entity.ProductLike;
import antigravity.entity.User;
import antigravity.exception.AntigravityException;
import antigravity.exception.ErrorCode;
import antigravity.repository.ProductLikeRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ProductLikeService {

	private final ProductLikeRepository productLikeRepository;
	private final ProductService productService;
	private final UserService userService;

	@Transactional(timeout = 10)
	public void saveProductLike(Long productId, Long userId) {
		Product product = productService.findProductById(productId);
		User user = userService.findUserById(userId);

		Optional<ProductLike> optionalProductLike = productLikeRepository.findByProductAndUser(product, user);

		if (optionalProductLike.isPresent()) {
			throw new AntigravityException(ErrorCode.ALREADY_LIKED_PRODUCT,
				String.format("userId=%d is already liked productId=%d", userId, productId));
		}

		ProductLike productLike = ProductLike.from(product, user);
		productLikeRepository.save(productLike);

		product.increaseViewed();
	}

}
