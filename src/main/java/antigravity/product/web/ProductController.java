package antigravity.product.web;

import antigravity.product.service.ProductService;
import antigravity.product.web.dto.ProductResponse;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.awt.print.Pageable;
import java.util.List;

@RestController
@RequestMapping("products/liked/")
public class ProductController {
    private ProductService productService;

    // TODO 찜 상품 등록 API
    @PostMapping("{productId}")
    public ResponseEntity<Long> postDipProduct(@RequestHeader("X-USER-ID") Integer userId, @PathVariable @NotNull(message="필수값입니다.") Long productId) {
        productService.createDip(userId, productId);
        return ResponseEntity.status(HttpStatus.CREATED).body(productId);
    }

    // TODO 찜 상품 조회 API
    // primitve type은 required = false 불가능
    @Validated
    @GetMapping()
    public ResponseEntity<List<ProductResponse>> getDipProductList(@RequestHeader("X-USER-ID") Integer userId, @RequestParam(value = "liked", required = false) Boolean liked, @PageableDefault(size=20, direction = Sort.Direction.ASC) Pageable pageable) {
        List<ProductResponse> productList = productService.findProductList(userId, liked, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(productList);
    }
}
