package antigravity.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import antigravity.entity.Product;

public interface ProductRepository extends JpaRepository<Product,Long>{

	Page<Product> findAllByDeletedAtIsNull(Pageable pageable);

	Optional<Product> findByIdAndDeletedAtIsNull(Long productId);
}
