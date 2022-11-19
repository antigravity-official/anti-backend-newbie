package antigravity.controller;

import antigravity.global.exception.ErrorCode;
import antigravity.global.exception.NotFoundException;
import antigravity.global.response.SuccessResponse;
import antigravity.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    // TODO 찜 상품 등록 API
    @PostMapping("/products/liked/{productId}")
    public SuccessResponse addWish(@RequestHeader(value = "X-USER-ID") Long userId, @PathVariable Long productId) {
        synchronized (this) {
            productService.addWish(userId, productId);
        }
        return new SuccessResponse(HttpStatus.CREATED.toString(), "added to wishlist");
    }

    // TODO 찜 상품 조회 API

}
