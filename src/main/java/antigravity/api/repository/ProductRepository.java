package antigravity.api.repository;

import antigravity.entity.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("select distinct p FROM Product p left join fetch p.productLikes " +
            "where p.id = :userId")
    Optional<Product> findById(@Param("userId") Long userId);

    @Query("select distinct p FROM Product p left join fetch p.productLikes")
    List<Product> findAllProduct(Pageable pageable);

    @Query("select distinct p FROM Product p left join fetch p.productLikes " +
            "WHERE NOT EXISTS (select pl FROM ProductLike pl WHERE pl.user.id = :userId)")
    List<Product> findProductsNotInProductLike(@Param("userId") Long userId, Pageable pageable);
}
