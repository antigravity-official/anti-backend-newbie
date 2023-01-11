package antigravity.product.web;

import antigravity.product.service.LikeProductService;
import antigravity.product.service.ProductService;
import antigravity.product.web.dto.LikeProductResponse;
import antigravity.product.web.dto.ProductResponse;
import antigravity.product.web.presenter.ProductPresenter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/products/liked/")
@RequiredArgsConstructor
public class ProductController {
    private final ProductPresenter productPresenter;
    private final LikeProductService likeProductService;

    // TODO 찜 상품 등록 API
    @PostMapping("{productId}")
    public ResponseEntity<LikeProductResponse> dipProduct(@RequestHeader("X-USER-ID") Integer userId, @PathVariable @NotNull(message="필수값입니다.") Long productId) {
        LikeProductResponse dip = likeProductService.createLikeProduct(userId, productId);
        return ResponseEntity.status(HttpStatus.CREATED).body(dip);
    }

    // TODO 찜 상품 조회 API
    // primitve type은 required = false 불가능
    @Validated
    @GetMapping()
    public ResponseEntity<Page<ProductResponse>> getDipProductList(@RequestHeader("X-USER-ID") Integer userId, @RequestParam(value = "liked", required = false) Boolean liked, @PageableDefault(size=20, direction = Sort.Direction.DESC) Pageable pageable) {
        Page<ProductResponse> productList = productPresenter.showProducts(userId, liked, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(productList);
    }
}
