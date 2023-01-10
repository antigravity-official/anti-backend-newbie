package antigravity.repository;

import antigravity.entity.ProductViewCount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductViewCountRepository extends JpaRepository<ProductViewCount, Long> {
}
