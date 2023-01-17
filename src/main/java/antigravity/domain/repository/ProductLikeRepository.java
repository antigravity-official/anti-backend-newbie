package antigravity.domain.repository;

import antigravity.domain.Product;
import antigravity.domain.ProductLike;
import antigravity.domain.User;

public interface ProductLikeRepository {

    Long save(ProductLike productLike);
    ProductLike existsByProductAndUser(Product product, User user);


}
