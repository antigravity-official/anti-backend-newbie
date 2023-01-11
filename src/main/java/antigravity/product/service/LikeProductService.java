package antigravity.product.service;

import antigravity.product.web.dto.LikeProductResponse;


public interface LikeProductService {
    LikeProductResponse createDip(Integer userId, Long productId);
}
