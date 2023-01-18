package antigravity.controller;

import antigravity.application.ProductLikeService;
import antigravity.application.dto.ProductLikeResponse;
import antigravity.common.ApiResponse;
import antigravity.common.Login;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductController {

    private final ProductLikeService productLikeService;

    // 찜 상품 등록 API
    @PostMapping("/{productId}")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<ProductLikeResponse> registerProductLike(@Login Long loginId, @PathVariable Long productId) {
        return ApiResponse.success(productLikeService.like(loginId, productId));
    }

    // TODO 찜 상품 조회 API

}
