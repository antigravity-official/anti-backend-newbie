package antigravity.api.repository;

import antigravity.entity.Product;
import antigravity.entity.ProductLike;
import antigravity.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductLikeRepository extends JpaRepository<ProductLike, Long> {
    Boolean existsByUserAndProduct(User findUser, Product findProduct);
}
