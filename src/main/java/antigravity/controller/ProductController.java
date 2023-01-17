package antigravity.controller;

import antigravity.entity.Product;
import antigravity.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
public class ProductController {

    private final ProductRepository productRepository;

    // TODO 상품 ID로 조회
    @GetMapping("/products/{productId}")
    public Product findById(@PathVariable String productId) {
        return productRepository.findById(Long.parseLong(productId));
    }

    // TODO 찜 상품 등록 API
    @PostMapping("/products/liked/{productId}")
    public ResponseEntity wantProduct(@PathVariable String productId,
                                      @RequestHeader(value = "X-USER-ID") String userNo) {
        try {
            //혹시 모를 빈칸 처리
            if (userNo.equals("")) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("유저 정보가 유효하지 않음.");
            } else {

                //유저 유효성
                if (productRepository.findUser(userNo) == 0) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("유저 정보가 유효하지 않음");
                }

                //상품 유효성
                if (productRepository.findById(productId) == 0) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("상품 정보가 유효하지 않음");
                }

                // 찜하기 조회
                if (productRepository.likeCheckSum(userNo, productId) != 0) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("이미 찜 목록에 있습니다.");
                }

                //혹시 모를 트랜젝션 에러
                if (productRepository.insertLikeProduct(userNo, productId) > 0) {
                    productRepository.updateView(productId);
                }

            }

        } catch (NullPointerException | HttpMessageNotReadableException e) {
            // userNo 의 널값 처리
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("유저 정보가 유효하지 않음");
        }

        return ResponseEntity.status(HttpStatus.CREATED).body("userNo = " + userNo + "\nproductId = " + productId);
    }

    // TODO 찜 상품 조회 API
    @GetMapping("/products")
    public ResponseEntity selectLikeList(@RequestHeader(value = "X-USER-ID") String userNo,
                                        @RequestParam(defaultValue = "1") Integer page,
                                        @RequestParam(defaultValue = "1") Integer size,
                                        @RequestParam(required = false) String liked) {

        List<Product> result;
        try {
            if (liked.equals("ture")) {
                result = productRepository.selectLikeProduct(userNo, page, size);
            } else {
                result = productRepository.selectDontLikeProduct(userNo, page, size);
            }

        } catch (NullPointerException e) {
            result = productRepository.selectAllProduct(userNo, page, size);
        }
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
