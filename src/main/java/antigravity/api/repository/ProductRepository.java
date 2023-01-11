package antigravity.api.repository;

import antigravity.entity.Product;
import antigravity.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.LockModeType;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("SELECT DISTINCT p FROM Product p LEFT JOIN FETCH p.productLikes")
    List<Product> findAllProduct();

    @Query("SELECT DISTINCT p FROM Product p LEFT JOIN FETCH p.productLikes " +
            "WHERE p.id = :productId")
    Optional<Product> findById(@Param("productId") Long productId);

    @Query("SELECT DISTINCT p FROM Product p LEFT JOIN FETCH p.productLikes " +
            "WHERE p NOT IN (SELECT pl.product FROM ProductLike pl WHERE pl.user = :user) ")
    List<Product> findProductsNotInProductLike(@Param("user") User user);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT p FROM Product p WHERE p.id =: id")
    Optional<Product> findByForUpdateByProductId(@Param("productId") Long productId);
}