package antigravity.product.domain.repository;


import antigravity.product.domain.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("UPDATE Product p SET p.viewed = :viewCnt WHERE p.id = :productId")
    void updateViewCntFromRedis(@Param("productId") Long productId, @Param("viewCnt") Long viewCnt);

    @Query("SELECT p.viewed from Product p WHERE p.id = :productId")
    Long findProductViewCnt(@Param("productId") Long productId);
}
