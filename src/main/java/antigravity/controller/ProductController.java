package antigravity.controller;

import antigravity.dto.payload.ProductResponse;
import antigravity.dto.payload.StatusResponseDto;
import antigravity.entity.Product;
import antigravity.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/{userId}")
public class ProductController {

    private ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products")
    public List<ProductResponse> liked() {
        return productService.findProducts();

    }

    // TODO 찜 상품 등록 API
    @PostMapping("/products/liked/{productId}")
    public ResponseEntity<?> postLikeProduct(@PathVariable Long productId,
                                             @PathVariable Long userId) {
        String msg = productService.likePost(productId, userId);
        return (msg.equals("success!"))
                ? new ResponseEntity<>(new StatusResponseDto("성공"), HttpStatus.CREATED)
                : new ResponseEntity<>(new StatusResponseDto(msg), HttpStatus.BAD_REQUEST);
    }

    // TODO 찜 상품 조회 API
    @GetMapping("/products/liked")
    public List<ProductResponse> getLikeProduct(@RequestParam(name = "liked",required = false) Boolean liked,
                                        @PathVariable Long userId) {
        return productService.likeGet(liked,userId);
    }
}
