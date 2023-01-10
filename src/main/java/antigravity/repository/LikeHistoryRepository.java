package antigravity.repository;

import antigravity.entity.LikeHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeHistoryRepository extends JpaRepository<LikeHistory, Long> {
    boolean existsByMemberIdAndProductId(Long memberId, Long productId);
}
