package antigravity.web.controller;

import antigravity.application.ProductService;
import antigravity.web.payload.common.SuccessMessages;
import antigravity.web.argumentresolver.UserInfo;
import antigravity.web.payload.common.ApplicationResponseEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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

}
