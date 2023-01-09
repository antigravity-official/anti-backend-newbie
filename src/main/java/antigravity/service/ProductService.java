package antigravity.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import antigravity.entity.Product;
import antigravity.entity.User;
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
			.orElseThrow(() -> new IllegalStateException("Product Not Found"));

		if (product.isDeleted()) {
			throw new IllegalStateException("Already Product Deleted");
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
			products = productRepository.findAllNotLikeProduct(user, pageable);
		} else {
			products = productRepository.findAllLikeProduct(user, pageable);
		}

		if (products.isEmpty()) {
			throw new IllegalStateException("Nonexistent Product");
		}

		return ProductResponse.from(products, user);
	}

}
