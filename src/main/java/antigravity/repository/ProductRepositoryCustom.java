package antigravity.repository;

import java.util.List;

import antigravity.entity.Product;
import antigravity.payload.ProductSearch;

public interface ProductRepositoryCustom {

	List<Product> findProducts(ProductSearch productSearch);

	List<Product> findProductsLikedByUser(Long userId, ProductSearch productRequest);

	List<Product> findProductsUnLikedByUser(Long userId, ProductSearch productRequest);
}
