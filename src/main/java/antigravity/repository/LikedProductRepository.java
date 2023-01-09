package antigravity.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import antigravity.entity.LikedProduct;
import antigravity.entity.Product;
import antigravity.entity.User;

public interface LikedProductRepository extends JpaRepository<LikedProduct, Long> {
	Optional<LikedProduct> findByUserAndProduct(User user, Product product);
}
