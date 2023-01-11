package antigravity.controller;


import antigravity.payload.ProductResponse;
import antigravity.payload.response.CommonResponse;
import antigravity.service.LikedProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("products")
@RestController
@AllArgsConstructor
public class ProductController {
    private final LikedProductService likedProductService;

    @PostMapping("liked/{productId}")
    public ResponseEntity<CommonResponse<Void>> createLikedProduct(@RequestHeader("X-USER-ID") Integer userId, @PathVariable long productId) {
        likedProductService.createLikedProduct(Long.valueOf(userId), productId);
        System.out.println("유저아이디 :" + userId + "상품아이디 : " + productId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    // TODO 찜 상품 조회 API
    @GetMapping("liked")
    public ResponseEntity<CommonResponse<List<ProductResponse>>> getLikedProducts(
            @RequestHeader("X-USER-ID") Integer userId,
            @RequestParam(required = false) Boolean liked,
            @RequestParam(required = true, defaultValue = "1") Integer page,
            @RequestParam(required = true, defaultValue = "10") Integer size) {
        return ResponseEntity.status(HttpStatus.OK).body(CommonResponse.success(
                likedProductService.getLikedProductsByUser(userId, liked, page, size)));
    }
}
