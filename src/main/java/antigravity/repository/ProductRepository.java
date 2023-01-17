package antigravity.repository;


import antigravity.entity.Product;
import antigravity.payload.ProductResponse;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ProductRepository extends JpaRepository<Product, Long> {

    default boolean insertProduct(Long productId) { return false; }

}
