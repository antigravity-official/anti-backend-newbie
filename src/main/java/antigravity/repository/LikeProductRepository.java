package antigravity.repository;

import antigravity.entity.LikeProduct;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeProductRepository extends JpaRepository<LikeProduct, Long> {

    Optional<LikeProduct> findByUserId(Long userId);
}
