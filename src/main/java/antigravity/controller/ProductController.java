package antigravity.controller;

import antigravity.dto.payload.ProductResponse;
import antigravity.dto.payload.StatusResponseDto;
import antigravity.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
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
    @PostMapping("/products/liked/{productId}/{userId}")
    public ResponseEntity<?> liked(@PathVariable Long productId,
                                @PathVariable Long userId) {
        String msg = productService.likePost(productId, userId);
        return (msg.equals("success!"))
                ? new ResponseEntity<>(new StatusResponseDto("성공"), HttpStatus.CREATED)
                : new ResponseEntity<>(new StatusResponseDto(msg), HttpStatus.BAD_REQUEST);
    }

    // TODO 찜 상품 조회 API

}
