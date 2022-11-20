package antigravity.repository;

import antigravity.entity.Product;
import antigravity.payload.ProductRequest;
import org.springframework.data.relational.core.sql.LockMode;
import org.springframework.data.relational.repository.Lock;

import java.util.List;

public interface ProductRepository extends CommonRepository<Product> {

    @Lock(LockMode.PESSIMISTIC_WRITE)
    void updateViewCount(Product product);

    List<Product> getProductList(ProductRequest request);

    List<Product> getWishList(Long userId, ProductRequest request);

    List<Product> getNotWishProductList(Long userId, ProductRequest request);
}
