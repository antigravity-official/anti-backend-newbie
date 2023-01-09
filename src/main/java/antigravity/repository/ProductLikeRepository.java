package antigravity.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import antigravity.entity.Product;
import antigravity.entity.ProductLike;
import antigravity.entity.User;

public interface ProductLikeRepository extends JpaRepository<ProductLike, Long> {
	Optional<ProductLike> findByProductAndUser(Product product, User user);

}
