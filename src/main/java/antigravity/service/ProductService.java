package antigravity.service;

import org.springframework.stereotype.Service;

import antigravity.entity.Product;
import antigravity.repository.ProductRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ProductService {

	private final ProductRepository productRepository;

	public Product findProductById(Long productId) {
		Product product = productRepository.findById(productId)
			.orElseThrow(() -> new IllegalStateException("Product Not Found"));

		if (product.isDeleted()) {
			throw new IllegalStateException("Already Product Deleted");
		}

		return product;
	}

}
