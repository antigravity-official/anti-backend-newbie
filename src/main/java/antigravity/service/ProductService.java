package antigravity.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import antigravity.entity.Product;
import antigravity.entity.User;
import antigravity.exception.AntigravityException;
import antigravity.exception.ErrorCode;
import antigravity.payload.ProductResponse;
import antigravity.repository.ProductRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ProductService {

	private final ProductRepository productRepository;
	private final UserService userService;

	@Transactional(readOnly = true)
	public Product findProductById(Long productId) {
		Product product = productRepository.findById(productId)
			.orElseThrow(() -> new AntigravityException(ErrorCode.PRODUCT_ID_NOT_FOUND,
				String.format("%d is not founded", productId)));

		if (product.isDeleted()) {
			throw new AntigravityException(ErrorCode.ALREADY_DELETED_PRODUCT,
				String.format("%d already deleted", productId));
		}

		return product;
	}

	@Transactional(readOnly = true)
	public Page<ProductResponse> getProducts(Long userId, Pageable pageable, Boolean liked) {
		User user = userService.findUserById(userId);

		Page<Product> products;

		if (liked == null) {
			products = productRepository.findAll(pageable);
		} else if (!liked) {
			products = productRepository.findAllUnlikeProduct(user, pageable);
		} else {
			products = productRepository.findAllLikeProduct(user, pageable);
		}

		if (products.isEmpty()) {
			throw new AntigravityException(ErrorCode.NONEXISTENT_PRODUCTS,
				String.format("userId=%d, pageable=%s, liked=%s", userId, pageable, liked));
		}

		return ProductResponse.from(products, user);
	}

}
