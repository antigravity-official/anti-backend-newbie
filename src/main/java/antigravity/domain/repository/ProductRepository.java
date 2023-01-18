package antigravity.domain.repository;

import antigravity.domain.Product;
import antigravity.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ProductRepository {

    Optional<Product> findById(Long productId);

    Page<Product> findLikedProducts(User user, Pageable pageable);

    Page<Product> findAnyProducts(Pageable pageable);

    Page<Product> findNotLikedProducts(User user, Pageable pageable);
}
