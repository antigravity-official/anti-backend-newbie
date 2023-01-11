package antigravity.product.web.presenter;

import antigravity.product.service.LikeProductService;
import antigravity.product.service.ProductService;
import antigravity.product.web.dto.ProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductPresenter {
    private final ProductService productService;
    private final LikeProductService likeProductService;

    public Page<ProductResponse> showProducts(Integer userId, Boolean liked, Pageable pageable) {
        if(liked == null) {
            return productService.findAllProductList(userId, pageable);
        } else if (liked) {
            return likeProductService.findLikeProduct(userId,pageable);
        } else {
            return productService.findNotLikeProductList(userId, pageable);
        }
    }
}
