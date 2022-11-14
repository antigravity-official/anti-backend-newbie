package antigravity.repository;

import antigravity.entity.ProductHits;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.LockModeType;
import javax.persistence.QueryHint;
import java.util.List;

public interface ProductHitsRepository extends JpaRepository<ProductHits, Long> {
    @Query("select p from ProductHits p where p.product.id in :products")
    List<ProductHits> findByProduct(Long[] products);

    @Transactional
    @QueryHints({@QueryHint(name = "javax.persistence.lock.timeout", value ="3000")})
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select p from ProductHits p join fetch p.product where p.product.id = :productId")
    ProductHits findByProductId(Long productId);
}
