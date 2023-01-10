package antigravity.controller;

import antigravity.Service.ProductService;
import antigravity.payload.ProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/products")
    public ResponseEntity<Page<ProductResponse>> getLikedProduct(
            @RequestHeader("X-USER-ID") Long userId,
            @RequestParam(required = false, value = "liked") Boolean liked,
            Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(productService.selectProduct(liked,userId,pageable));
    }
}
