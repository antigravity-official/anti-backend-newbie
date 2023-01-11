package antigravity.product.adapter.out.persistence;

import antigravity.product.application.port.in.dto.GetProductsOutput;
import antigravity.product.adapter.out.persistence.cond.GetProductsCond;
import antigravity.product.application.port.out.LoadProductPort;
import antigravity.product.domain.Product;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
class ProductPersistenceAdapter implements LoadProductPort {

	private final ProductRepository productRepository;

	@Override
	public Optional<Product> getProduct(Long productId) {
		return productRepository.findById(productId);
	}

	@Override
	public Page<GetProductsOutput> getPagingProducts(GetProductsCond cond, Pageable pageable) {
		return productRepository.getProducts(pageable, cond);
	}

}
