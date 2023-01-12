package antigravity.repository;

import antigravity.entity.ProductHits;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductHitsRepository extends JpaRepository<ProductHits,Long> {
    Optional<ProductHits> findByProductId(Long productId);
}
