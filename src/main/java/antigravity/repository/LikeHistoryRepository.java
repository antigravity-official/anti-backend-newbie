package antigravity.repository;

import antigravity.entity.LikeHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface LikeHistoryRepository extends JpaRepository<LikeHistory, Long> {
    boolean existsByMemberIdAndProductId(Long memberId, Long productId);


    @Query("select count(h.id) from LikeHistory h where h.product.id = :id")
    Long getTotalLike(Long productId);

}
