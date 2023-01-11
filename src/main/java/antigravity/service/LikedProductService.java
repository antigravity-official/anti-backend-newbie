package antigravity.service;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import antigravity.entity.LikedProduct;
import antigravity.entity.Product;
import antigravity.entity.User;
import antigravity.exception.custom.AlreadyLikedException;
import antigravity.exception.custom.ProductNotFoundException;
import antigravity.exception.custom.UserNotFoundException;
import antigravity.payload.LikedProductResponse;
import antigravity.repository.LikedProductRepository;
import antigravity.repository.ProductRepository;
import antigravity.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LikedProductService {
	private final ProductRepository productRepository;
	private final LikedProductRepository likedProductRepository;
	private final UserRepository userRepository;

	@Transactional
	public ResponseEntity<LikedProductResponse> registerLikedProduct(Long productId, Long userId) {
		LikedProduct createdLikedProduct = null;
		User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
		Product product = productRepository.findById(productId).orElseThrow(ProductNotFoundException::new);
		Optional<LikedProduct> productLikedByUser = likedProductRepository.findByUserAndProduct(user, product);

		if (productLikedByUser.isEmpty()) {
			createdLikedProduct = likedProductRepository.save(new LikedProduct(user, product));
		} else {
			if (productLikedByUser.get().isLiked()) {
				throw new AlreadyLikedException();
			}
		}
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(new LikedProductResponse(
						createdLikedProduct != null ? createdLikedProduct.getUser().getId() : null,
						createdLikedProduct != null ? createdLikedProduct.getProduct().getId() : null));
	}
}
