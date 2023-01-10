package antigravity.product.service;

import antigravity.product.web.dto.DipProductResponse;
import antigravity.product.web.dto.ProductResponse;

import java.awt.print.Pageable;
import java.util.List;

public interface ProductService {
    DipProductResponse createDip(Integer userId, Long productId);
    List<ProductResponse> findProductList(Integer userId, boolean liked, Pageable pageable);
}
