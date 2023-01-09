package antigravity.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import antigravity.entity.Product;
import antigravity.entity.User;

public interface ProductRepository extends JpaRepository<Product, Long> {

	@Query("SELECT p FROM Product p JOIN ProductLike pl ON pl.product = p WHERE pl.user = :user AND p.deletedAt is null")
	Page<Product> findAllLikeProduct(@Param("user") User user, Pageable pageable);

	@Query("SELECT p FROM Product p WHERE p NOT IN (SELECT pl.product FROM ProductLike pl WHERE pl.user = :user) AND p.deletedAt is null")
	Page<Product> findAllNotLikeProduct(@Param("user") User user, Pageable pageable);

}
