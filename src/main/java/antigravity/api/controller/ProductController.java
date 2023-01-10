package antigravity.api.controller;


import antigravity.api.service.ProductLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {

    private final ProductLikeService productLikeService;

    /**
     * 과제 1. 찜 상품 등록 API
     *
     * @param userId
     * @param productId
     * @return User or Product Not Found -> 400 Bad Request, else 201 Created
     */
    @PostMapping("/liked/{productId}")
    public ResponseEntity<HttpStatus> likedProductAdd(@RequestHeader(name = "X-USER-ID") Integer userId,
                                                      @PathVariable("productId") Long productId){
        productLikeService.addLikedProduct(userId, productId);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    // TODO 찜 상품 조회 API
}
