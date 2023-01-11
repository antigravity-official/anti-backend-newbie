package antigravity.web.controller;

import antigravity.application.ProductService;
import antigravity.web.argumentresolver.UserInfo;
import antigravity.web.common.ApplicationResponseEntity;
import antigravity.web.common.SuccessMessages;
import antigravity.domain.product.LikeStatus;
import antigravity.web.response.ProductListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class ProductController {

    private final ProductService productService;

    // TODO 찜 상품 등록 API
    @PostMapping("/products/liked/{productId}")
    public ApplicationResponseEntity<Void> add(@UserInfo Long userId, @PathVariable Long productId) {

        productService.likeProduct(userId, productId);
        return new ApplicationResponseEntity<>(SuccessMessages.PRODUCT_LIKE_SUCCESS,
            HttpStatus.CREATED);
    }

    // TODO 찜 상품 조회 API
    @GetMapping("/products/liked")
    public ApplicationResponseEntity<ProductListResponse> searchProducts(@UserInfo Long userId, LikeStatus likeStatus, @PageableDefault Pageable pageable) {

        ProductListResponse products = productService.searchProducts(userId, likeStatus, pageable);
        return new ApplicationResponseEntity<>(SuccessMessages.SEARCH_PRODUCTS_SUCCESS, products,
            HttpStatus.CREATED);
    }

}
