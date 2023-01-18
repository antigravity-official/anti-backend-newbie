package antigravity.domain.repository;


import antigravity.domain.ProductView;

import java.util.Optional;

public interface ProductViewRepository {

    Optional<ProductView> findByProductId(Long productId);

    void updateViewCount(Long productId, Long viewCount);

}
