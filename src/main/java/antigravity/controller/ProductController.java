package antigravity.controller;

import antigravity.payload.ProductResponse;
import antigravity.payload.ProductSearchCriteria;
import antigravity.payload.ApiResponse;
import antigravity.service.ProductLikeService;
import antigravity.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final ProductLikeService productLikeService;


    // TODO 찜 상품 등록 API
    @PostMapping(path = "/products/liked/{productId}")
    public ResponseEntity<Object> likedProduct(@RequestHeader(name = "X-USER-ID") Long userId,
                                               @PathVariable Long productId) {
        productLikeService.likedProduct(userId, productId);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    // TODO 찜 상품 조회 API
    @GetMapping(path = "/products")
    public ResponseEntity<Object> getProducts(@RequestHeader(name = "X-USER-ID") Integer userId,
                                                 ProductSearchCriteria productSearchCriteria) {
        List<ProductResponse> productResponse = productService.findProducts(userId.longValue(), productSearchCriteria);
        ApiResponse<List<ProductResponse>> result = ApiResponse.success(productResponse);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
