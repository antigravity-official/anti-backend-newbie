package antigravity.repository;

import antigravity.entity.LikedStatus;
import antigravity.entity.View;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ViewRepository extends JpaRepository<View, Long> {

    List<View> findAllByProductIdAndMemberIdAndLikedStatus(Long productId, Long memberId, LikedStatus likedStatus);
}
