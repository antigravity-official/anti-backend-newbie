package antigravity.infra;

import antigravity.domain.Product;
import antigravity.domain.ProductLike;
import antigravity.domain.User;
import antigravity.domain.repository.ProductLikeRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductLikeJpaRepository extends JpaRepository<ProductLike, Long>, ProductLikeRepository {
    @Override
    Optional<ProductLike> findByProductAndUser(Product product, User user);
}
