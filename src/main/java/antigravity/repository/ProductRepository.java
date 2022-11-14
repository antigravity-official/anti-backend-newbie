package antigravity.repository;

import antigravity.entity.Product;
import antigravity.repository.custom.ProductCustomRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long>, ProductCustomRepository {
}
