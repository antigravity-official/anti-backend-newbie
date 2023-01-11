package antigravity.product.web.presenter;

import antigravity.product.service.LikeProductService;
import antigravity.product.service.ProductService;
import antigravity.product.web.dto.ProductResponse;
import antigravity.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductPresenter {
    private final ProductService productService;
    private final LikeProductService likeProductService;
    private final UserService userService;

    public Page<ProductResponse> showProducts(Integer userId, Boolean liked, Pageable pageable) {
        //전체 조회
        if(liked == null) {
            return productService.findAllProductList(userId, pageable);
        }
        else if (liked) { // 찜한 제품만 조회
            return likeProductService.findLikeProduct(userId,pageable);
        }
        else { // 찜하지 않은 제품만 조회
            return productService.findNotLikeProductList(userId, pageable);
        }
    }
}
