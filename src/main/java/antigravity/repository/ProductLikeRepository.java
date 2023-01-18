package antigravity.repository;

import antigravity.entity.Product;
import antigravity.entity.ProductLike;
import antigravity.entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductLikeRepository extends JpaRepository<ProductLike, Long> {
    Boolean existsByUserAndProduct(User user, Product product);

    List<ProductLike> findAllByUserId(Long userId, Pageable pageable);
}
