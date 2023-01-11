package antigravity.product.service;

import antigravity.product.web.dto.LikeProductResponse;
import antigravity.product.web.dto.ProductResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface LikeProductService {
    LikeProductResponse createLikeProduct(Integer userId, Long productId);
    Page<ProductResponse> findLikeProduct(Integer userId, Pageable pageable);
}
