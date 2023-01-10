package antigravity.infra.respository;

import antigravity.domain.product.Product;
import antigravity.domain.product.ProductRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaProductRepository extends ProductRepository, JpaRepository<Product, Long> {

}
