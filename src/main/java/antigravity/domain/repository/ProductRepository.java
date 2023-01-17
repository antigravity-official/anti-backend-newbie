package antigravity.domain.repository;

import antigravity.domain.Product;

import java.util.Optional;

public interface ProductRepository {

    Optional<Product> findById(Long productId);
}
