package antigravity.repository;

import antigravity.entity.Liked;
import antigravity.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.List;

@Repository
public interface LikedRepository extends JpaRepository<Liked, Long> {

    @Query("select l from Liked l where l.productId = :productId")
    List<Liked> findAllByProductId(@Param("productId") Product product);

    List<Long> findProductIdByUserId(Long userId, Pageable pageable);

    Long countByProductId(Long productId);
}
