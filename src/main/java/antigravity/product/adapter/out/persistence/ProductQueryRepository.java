package antigravity.product.adapter.out.persistence;

import antigravity.product.application.port.in.dto.GetProductsOutput;
import antigravity.product.adapter.out.persistence.cond.GetProductsCond;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

interface ProductQueryRepository {

	Page<GetProductsOutput> getProducts(Pageable pageable, GetProductsCond cond);
}
