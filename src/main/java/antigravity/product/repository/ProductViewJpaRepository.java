package antigravity.product.repository;

import antigravity.product.domain.ProductView;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductViewJpaRepository extends JpaRepository<ProductView, Long> {

}
