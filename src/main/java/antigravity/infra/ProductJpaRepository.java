package antigravity.infra;

import antigravity.domain.Product;
import antigravity.domain.User;
import antigravity.domain.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ProductJpaRepository extends JpaRepository<Product, Long>, ProductRepository {

    @Override
    @Query("select p from Product p where p.id = :productId and p.deletedAt = null")
    Optional<Product> findById(@Param("productId") Long productId);

    @Override
    @Query("select p from Product p join p.productLikes pl on pl.user = :user where p.deletedAt = null")
    Page<Product> findLikedProducts(@Param("user") User user, Pageable pageable);

    @Override
    @Query("select p from Product p where p.deletedAt = null")
    Page<Product> findAnyProducts(Pageable pageable);

    @Override
    @Query("select p from Product p" +
            " where p not in (select pl.product from ProductLike pl where pl.user = :user)" +
            " and p.deletedAt = null")
    Page<Product> findNotLikedProducts(@Param("user") User user, Pageable pageable);

}
