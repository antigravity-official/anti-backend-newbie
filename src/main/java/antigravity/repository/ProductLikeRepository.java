package antigravity.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import antigravity.entity.Product;
import antigravity.entity.ProductLike;
import antigravity.entity.User;

public interface ProductLikeRepository extends JpaRepository<ProductLike, Long> {

	@Query("SELECT pl FROM ProductLike pl WHERE pl.product = :product AND pl.user = :user")
	Optional<ProductLike> findByProductAndUser(@Param("product") Product product, @Param("user") User user);

}
