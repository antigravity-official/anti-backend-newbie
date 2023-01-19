package antigravity.repository;

import antigravity.entity.Liked;
import antigravity.entity.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LikedRepository extends JpaRepository<Liked, Long> {

    @Override
    Optional<Liked> findById(Long id);

    @Query(value = "SELECT COUNT(l) FROM Liked l WHERE l.user.id = :userId AND l.product.id = :productId")
    int countAlreadyLiked(@Param("userId") Long userId, @Param("productId") Long productId);

    @Query(value = "SELECT l.product.id FROM Liked l WHERE l.user.id = :userId")
    List<Long> findLikedProductId (@Param("userId")Long userId);

}
