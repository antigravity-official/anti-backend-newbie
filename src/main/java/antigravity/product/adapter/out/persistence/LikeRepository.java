package antigravity.product.adapter.out.persistence;

import antigravity.product.domain.Like;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

interface LikeRepository extends JpaRepository<Like, Long> {

	Optional<Like> findByUserIdAndProductId(Long userId, Long productId);

}
