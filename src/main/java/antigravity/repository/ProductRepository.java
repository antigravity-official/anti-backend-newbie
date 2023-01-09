package antigravity.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import antigravity.entity.Product;
import antigravity.entity.User;

public interface ProductRepository extends JpaRepository<Product, Long> {

	@Query("SELECT p FROM Product p JOIN ProductLike pl ON pl.product = p WHERE pl.user = :user AND p.deletedAt is null")
	Page<Product> findAllLikeProduct(User user, Pageable pageable);

	@Query("SELECT p FROM Product p JOIN ProductLike pl ON pl.product = p WHERE pl.user <> :user AND p NOT IN (SELECT p FROM p WHERE pl.user = :user) AND p.deletedAt is null")
	Page<Product> findAllNotLikeProduct(User user, Pageable pageable);

}
