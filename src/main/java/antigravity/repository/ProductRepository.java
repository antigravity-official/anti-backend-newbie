package antigravity.repository;

import antigravity.entity.Product;
import antigravity.entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("UPDATE Product p SET p.viewed = p.viewed +1 WHERE p.id = :productId")
    int increaseViewed(Long productId);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("UPDATE Product p SET p.totalLiked = p.totalLiked +1 WHERE p.id = :productId")
    int increaseTotalLiked(Long productId);

    @Query("SELECT p FROM Product p WHERE p.id NOT IN(SELECT product FROM ProductLike pl WHERE pl.user = :user) AND p.deletedAt = null")
    List<Product> findAllFalseLikedByUserId(User user, Pageable pageable);

    @Query("SELECT p FROM Product p WHERE p.deletedAt = null")
    List<Product> findAllExceptDeletedAt(Pageable pageable);
}
