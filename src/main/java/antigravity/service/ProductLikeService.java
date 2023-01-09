package antigravity.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import antigravity.entity.Product;
import antigravity.entity.ProductLike;
import antigravity.entity.User;
import antigravity.repository.ProductLikeRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ProductLikeService {

	private final ProductLikeRepository productLikeRepository;
	private final ProductService productService;
	private final UserService userService;

	public void productLike(Long productId, Integer userId) {
		Product product = productService.findProductById(productId);
		User user = userService.findUserById(userId);

		Optional<ProductLike> optionalProductLike = productLikeRepository.findByProductAndUser(product, user);

		if (optionalProductLike.isPresent()) {
			throw new IllegalStateException("Already User Liked Product");
		}

		ProductLike productLike = ProductLike.from(product, user);
		productLikeRepository.save(productLike);

		product.increaseViewed();
	}
}
