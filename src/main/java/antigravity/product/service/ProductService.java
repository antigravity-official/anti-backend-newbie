package antigravity.product.service;

import antigravity.product.web.dto.ProductResponse;

import java.awt.print.Pageable;
import java.util.List;

public interface ProductService {
    List<ProductResponse> findProductList(boolean liked, Pageable pageable);
}
