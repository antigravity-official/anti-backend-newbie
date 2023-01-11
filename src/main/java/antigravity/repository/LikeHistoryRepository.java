package antigravity.repository;

import antigravity.entity.LikeHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LikeHistoryRepository extends JpaRepository<LikeHistory, Long> {
    boolean existsByMemberIdAndProductId(Long memberId, Long productId);


    @Query("select count(h.id) from LikeHistory h where h.product.id = :productId")
    Long getTotalLike(@Param("productId") Long productId);

}
