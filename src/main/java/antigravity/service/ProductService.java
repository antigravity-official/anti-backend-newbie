package antigravity.service;


import antigravity.payload.ProductRequest;
import antigravity.payload.ProductResponse;

import java.util.List;

public interface ProductService {

    void addWish(Long userId, Long productId);
    List<ProductResponse> getProductOrWishList(Long userId, ProductRequest request);
}
