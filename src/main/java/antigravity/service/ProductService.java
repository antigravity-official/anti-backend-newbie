package antigravity.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
import antigravity.payload.ProductResponse;
import antigravity.payload.ProductSearch;
import antigravity.repository.LikedProductRepository;
import antigravity.repository.ProductRepository;
import antigravity.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {
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

	public ResponseEntity<List<ProductResponse>> findProducts(Long userId, ProductSearch productSearch) {
		User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);

		if (productSearch.getLiked()) {
			List<Product> productsLikedByUser = productRepository.findProductsLikedByUser(userId, productSearch);
			return ResponseEntity.status(HttpStatus.OK)
					.body(
							productsLikedByUser.stream()
									.map(p -> ProductResponse.builder()
											.id(p.getId())
											.sku(p.getSku())
											.name(p.getName())
											.price(p.getPrice())
											.quantity(p.getQuantity())
											.viewed(p.getViews())
											.liked(true)
											.totalLiked(p.getProductLiked().size())
											.createdAt(p.getCreatedAt())
											.updatedAt(p.getUpdatedAt())
											.build())
									.collect(Collectors.toList()));
		} else if (!productSearch.getLiked()) {
			return ResponseEntity.status(HttpStatus.OK)
					.body(
							productRepository.findProductsUnLikedByUser(user.getId(), productSearch).stream()
									.map(p -> ProductResponse.builder()
											.id(p.getId())
											.sku(p.getSku())
											.name(p.getName())
											.price(p.getPrice())
											.quantity(p.getQuantity())
											.viewed(p.getViews())
											.liked(false)
											.totalLiked(p.getProductLiked().size())
											.createdAt(p.getCreatedAt())
											.updatedAt(p.getUpdatedAt())
											.build())
									.collect(Collectors.toList()));
		}
		List<ProductResponse> products = new ArrayList<>();
		List<Product> all = productRepository.findProducts(productSearch);
		for (Product product : all) {
			if (product.getProductLiked().stream().anyMatch(p -> p.getUser().getId().equals(userId))) {
				ProductResponse response = ProductResponse.builder()
						.id(product.getId())
						.sku(product.getSku())
						.name(product.getName())
						.price(product.getPrice())
						.quantity(product.getQuantity())
						.viewed(product.getViews())
						.liked(true)
						.totalLiked(product.getProductLiked().size())
						.createdAt(product.getCreatedAt())
						.updatedAt(product.getUpdatedAt())
						.build();
				products.add(response);
			} else {
				ProductResponse response = ProductResponse.builder()
						.id(product.getId())
						.sku(product.getSku())
						.name(product.getName())
						.price(product.getPrice())
						.quantity(product.getQuantity())
						.viewed(product.getViews())
						.liked(false)
						.totalLiked(product.getProductLiked().size())
						.createdAt(product.getCreatedAt())
						.updatedAt(product.getUpdatedAt())
						.build();
				products.add(response);
			}
		}

		return ResponseEntity.status(HttpStatus.OK).body(products);
	}
}
