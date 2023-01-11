package antigravity.product.web;

import antigravity.product.service.LikeProductService;
import antigravity.product.web.dto.LikeProductResponse;
import antigravity.product.web.dto.ProductResponse;
import antigravity.product.web.presenter.ProductPresenter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@Api(value = "ProductController")
@RestController
@RequestMapping("/products/liked/")
@RequiredArgsConstructor
public class ProductController {
    private final ProductPresenter productPresenter;
    private final LikeProductService likeProductService;

    @ApiOperation(value = "Registration Like Product", notes = "찜 상품 등록")
    @PostMapping("{productId}")
    public ResponseEntity<LikeProductResponse> dipProduct(@RequestHeader("X-USER-ID") Integer userId, @PathVariable @NotNull(message="필수값입니다.") Long productId) {
        LikeProductResponse dip = likeProductService.createLikeProduct(userId, productId);
        return ResponseEntity.status(HttpStatus.CREATED).body(dip);
    }

    // primitive type은 required = false 불가능
    @ApiOperation(value = "Inquiry Like Product", notes = "찜 상품 조회")
    @Validated
    @GetMapping("")
    public ResponseEntity<Page<ProductResponse>> getDipProductList(@RequestHeader("X-USER-ID") Integer userId, @RequestParam(value = "liked", required = false) Boolean liked, Pageable pageable) {
        Page<ProductResponse> productList = productPresenter.showProducts(userId, liked, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(productList);
    }
}
