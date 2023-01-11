package antigravity.product.service;

import antigravity.product.domain.entity.Product;
import antigravity.product.web.dto.ProductResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface ProductService {
    Page<ProductResponse> findProductList(Integer userId, Boolean liked, Pageable pageable);
    Product validateExistProduct(Long productId);
    Page<Product> findAllProduct(Pageable pageable);
    Product findById(Long productId);
}
