package antigravity.repository;

import antigravity.entity.Basket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface BasketRepository extends JpaRepository<Basket, Long> {

    boolean existsBasketByProductIdAndUserId(Long productId, Long userId);
    List<IdMapping> findAllByUserId(Long userId);
}
