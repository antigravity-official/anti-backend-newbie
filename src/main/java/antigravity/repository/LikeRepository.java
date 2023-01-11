package antigravity.repository;

import antigravity.entity.Like;
import antigravity.entity.Member;
import antigravity.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {
    Optional<Like> findByProductAndMember(Product product, Member member);
}
