package antigravity.controller;

import antigravity.payload.LikeResponse;
import antigravity.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService service;

    // 찜 상품 등록 API
    @PostMapping("/liked/{productId}")
    public ResponseEntity<LikeResponse> likeProduct(@RequestHeader("X-USER-ID") Long memberId, @PathVariable("productId") Long productId) {
        LikeResponse likeResponse = new LikeResponse();
        int result =  service.isAlreadyLiked(memberId, productId);
        if(result > 0) {
            likeResponse = LikeResponse.builder()
                            .code(HttpStatus.CREATED.value())
                            .httpStatus(HttpStatus.CREATED)
                            .message("찜 등록 완료")
                            .build();
        } else {
            likeResponse = LikeResponse.builder()
                            .code(HttpStatus.BAD_REQUEST.value())
                            .httpStatus(HttpStatus.BAD_REQUEST)
                            .message("찜 등록 실패")
                            .build();
        }
        return new ResponseEntity<>(likeResponse, likeResponse.getHttpStatus());
    }

    // 찜 상품 조회 API

}
