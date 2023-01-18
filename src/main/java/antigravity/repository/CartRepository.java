package antigravity.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import antigravity.entity.Cart;


public interface CartRepository extends JpaRepository<Cart, Long> {
}
