package antigravity.controller;


import antigravity.entity.Product;
import antigravity.payload.ProductResponse;
import antigravity.repository.LikedProductRpository;
import antigravity.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    // TODO 찜 상품 등록 API
    @PostMapping("/products/liked/{productId}")
    public ResponseEntity saveLikedProduct(
            @NotNull @PathVariable Integer productId,
            @NotNull @RequestHeader("X-USER-ID") Integer userId
    ) {

        productService.registerLikeProduct(productId.longValue(), userId.longValue());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    // TODO 찜 상품 조회 API
    @GetMapping("/products/liked")
    public ResponseEntity<List<ProductResponse>> findByLikedStatus(
            @RequestParam Boolean liked,
            @RequestParam Integer page,
            @RequestParam Integer size
            ){

        return new ResponseEntity<>(null);
    }
}
