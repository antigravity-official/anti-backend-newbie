package antigravity.repository;

import antigravity.entity.LikeProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeProductRepository extends JpaRepository<LikeProduct, Long> {

    LikeProduct findByUserId(Long userId);
}
