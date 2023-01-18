package antigravity.infra;

import antigravity.domain.Product;
import antigravity.domain.repository.ProductRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ProductJpaRepository extends JpaRepository<Product, Long>, ProductRepository {

    @Override
    @Query("select p from Product p where p.id = :productId and p.deletedAt = null")
    Optional<Product> findById(@Param("productId") Long productId);
}
