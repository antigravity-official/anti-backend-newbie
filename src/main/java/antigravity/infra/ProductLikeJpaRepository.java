package antigravity.infra;

import antigravity.domain.Product;
import antigravity.domain.ProductLike;
import antigravity.domain.User;
import antigravity.domain.repository.ProductLikeRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface ProductLikeJpaRepository extends JpaRepository<ProductLike, Long>, ProductLikeRepository {
    @Override
    Optional<ProductLike> findByProductAndUser(Product product, User user);

    @Override
    @Query("select count(pl) from ProductLike pl where pl.deletedAt = null")
    Integer countByProduct(Product product);

    @Override
    @Query("select pl from ProductLike pl where pl.user = :user and pl.product in :products and pl.deletedAt = null")
    Set<ProductLike> findProductLikeByUserAndProducts(@Param("user") User user, @Param("products") List<Product> product);
}
