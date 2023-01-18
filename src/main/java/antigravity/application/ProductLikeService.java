package antigravity.application;

import antigravity.application.dto.ProductLikeResponse;

public interface ProductLikeService {

    ProductLikeResponse like(Long userId, Long productId);
}
