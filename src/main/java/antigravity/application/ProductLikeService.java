package antigravity.application;

import antigravity.application.dto.ProductRegisterResponse;

public interface ProductLikeService {

    ProductRegisterResponse like(Long userId, Long productId);
}
