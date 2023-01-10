package antigravity.product.service;

import antigravity.product.web.dto.ProductResponse;

import java.awt.print.Pageable;
import java.util.List;

public interface ProductService {
    Long createDip(Long userId, Long productId);
    List<ProductResponse> findProductList(Long userId, boolean liked, Pageable pageable);
}
