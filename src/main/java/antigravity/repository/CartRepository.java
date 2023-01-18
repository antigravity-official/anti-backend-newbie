package antigravity.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import antigravity.entity.Cart;
import antigravity.entity.Product;
import antigravity.entity.User;

public interface CartRepository extends JpaRepository<Cart, Long> {

	@EntityGraph(attributePaths = {"user"})
	Page<Cart> findByUser(User user, Pageable requestPage);

	List<Cart> findByUserAndProductIn(User user, List<Product> products);

}
