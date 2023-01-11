package antigravity.product.service;

import antigravity.product.web.dto.DipProductResponse;
import antigravity.product.web.dto.ProductResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface ProductService {
    DipProductResponse createDip(Integer userId, Long productId);
    Page<ProductResponse> findProductList(Integer userId, Boolean liked, Pageable pageable);
}
