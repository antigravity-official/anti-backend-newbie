package antigravity.product.service;

import antigravity.product.web.dto.ProductResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface ProductService {
    Page<ProductResponse> findAllProductList(Integer userId, Pageable pageable);
    Page<ProductResponse> findNotLikeProductList(Integer userId, Pageable pageable);
}
