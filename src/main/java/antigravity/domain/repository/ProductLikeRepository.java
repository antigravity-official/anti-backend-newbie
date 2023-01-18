package antigravity.domain.repository;

import antigravity.domain.Product;
import antigravity.domain.ProductLike;
import antigravity.domain.User;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface ProductLikeRepository {

    ProductLike save(ProductLike productLike);
    Optional<ProductLike> findByProductAndUser(Product product, User user);

    Integer countByProduct(Product product);

    Set<ProductLike> findProductLikeByUserAndProducts(User user, List<Product> product);


}
