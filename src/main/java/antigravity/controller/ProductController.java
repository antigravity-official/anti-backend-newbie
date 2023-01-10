package antigravity.controller;

import antigravity.payload.LikeResponse;
import antigravity.payload.ProductResponse;
import antigravity.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService service;

    // 찜 상품 등록
    @PostMapping("/liked/{productId}")
    public ResponseEntity<LikeResponse> likeProduct(@RequestHeader("X-USER-ID") Long memberId,
                                                    @PathVariable("productId") Long productId) {
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

    // 모든 상품 조회
    @GetMapping
    public ResponseEntity<List<ProductResponse>> getAllProduct(@RequestHeader("X-USER-ID") Long memberId,
                                                               @RequestParam(defaultValue = "1") int page,
                                                               @RequestParam(defaultValue = "10") int size) {
        int listSize = size;
        int startList = (page - 1) * listSize;
        List<ProductResponse> list = service.getAllProduct(memberId, listSize, startList);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    // 찜한 상품 조회
    @GetMapping("/liked")
    public ResponseEntity<List<ProductResponse>> getLikedProduct(@RequestHeader("X-USER-ID") Long memberId,
                                                                 @RequestParam(required = false) Boolean like,
                                                                 @RequestParam(defaultValue = "1") int page,
                                                                 @RequestParam(defaultValue = "10") int size) {
        int listSize = size;
        int startList = (page - 1) * listSize;
        List<ProductResponse> list = service.getLikedProduct(memberId, like, listSize, startList);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

}
