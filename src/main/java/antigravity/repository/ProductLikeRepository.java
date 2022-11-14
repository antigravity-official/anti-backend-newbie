package antigravity.repository;

import antigravity.entity.Product;
import antigravity.entity.ProductLike;
import antigravity.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductLikeRepository extends JpaRepository<ProductLike, Long> {
    boolean existsByUserAndProduct(User user, Product product);
}
