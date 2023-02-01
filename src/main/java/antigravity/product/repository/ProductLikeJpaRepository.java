package antigravity.product.repository;

import antigravity.product.domain.ProductLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductLikeJpaRepository extends JpaRepository<ProductLike, Long> {

    boolean existsByProductIdAndUserId(Long productId, Long userId);
}
