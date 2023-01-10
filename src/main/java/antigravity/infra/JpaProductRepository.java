package antigravity.infra;

import antigravity.domain.Product;
import antigravity.domain.ProductRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaProductRepository extends ProductRepository, JpaRepository<Product, Long> {

}
