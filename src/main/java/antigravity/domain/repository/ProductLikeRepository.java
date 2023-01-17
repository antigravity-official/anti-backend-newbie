package antigravity.domain.repository;

import antigravity.domain.Product;
import antigravity.domain.ProductLike;
import antigravity.domain.User;

import java.util.Optional;

public interface ProductLikeRepository {

    ProductLike save(ProductLike productLike);
    Optional<ProductLike> findByProductAndUser(Product product, User user);


}
