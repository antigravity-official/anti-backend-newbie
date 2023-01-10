package antigravity.product.domain.repository;

import antigravity.product.domain.entity.DipProduct;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DipProductRepository extends JpaRepository<DipProduct, Long> {
    Optional<Page<DipProduct>> findAllByUserId(Pageable pageable);
    boolean existsDipProductByUserIdAndProductId(Long userId, Long productId);
}
