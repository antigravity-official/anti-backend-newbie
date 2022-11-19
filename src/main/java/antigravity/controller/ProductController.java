package antigravity.controller;

import antigravity.global.response.SuccessResponse;
import antigravity.payload.ProductRequest;
import antigravity.payload.ProductResponse;
import antigravity.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Size;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    // TODO 찜 상품 등록 API
    @PostMapping("/liked/{productId}")
    public SuccessResponse addWish(@RequestHeader(value = "X-USER-ID") Long userId, @PathVariable Long productId) {
        synchronized (this) {
            productService.addWish(userId, productId);
        }
        return new SuccessResponse(HttpStatus.CREATED.toString(), "added to wishlist");
    }

    // TODO 찜 상품 조회 API
    @GetMapping
    public SuccessResponse getWishList(@RequestHeader(value = "X-USER-ID") Long userId,
                                       @ModelAttribute @Valid ProductRequest productRequest) {
        List<ProductResponse> responses = productService.getProductOrWishList(userId, productRequest);
        return new SuccessResponse(HttpStatus.OK.toString(), responses);
    }

}
