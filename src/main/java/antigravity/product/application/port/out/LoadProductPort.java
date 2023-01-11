package antigravity.product.application.port.out;

import antigravity.product.application.port.in.dto.GetProductsOutput;
import antigravity.product.adapter.out.persistence.cond.GetProductsCond;
import antigravity.product.domain.Product;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface LoadProductPort {

	Optional<Product> getProduct(Long productId);

	Page<GetProductsOutput> getPagingProducts(GetProductsCond cond, Pageable pageable);

}
