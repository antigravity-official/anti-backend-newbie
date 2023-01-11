package antigravity.product.application;

import antigravity.product.domain.Product;

public interface ProductRepository {

    Product getById(Long productId);

    void increseViewsByProductId(Long productId);

    void like(Long productId, Long userId);
}
