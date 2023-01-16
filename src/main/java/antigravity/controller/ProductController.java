package antigravity.controller;

import antigravity.entity.Product;
import antigravity.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@Slf4j
@RequiredArgsConstructor
public class ProductController {

    private final ProductRepository productRepository;

    @PostMapping("/user")


    // TODO 상품 ID로 조회
    @GetMapping("/products/{productId}")
    public Product findById(@PathVariable String productId) {
        return productRepository.findById(Long.parseLong(productId));
    }

    // TODO 찜 상품 등록 API
    @PostMapping("/products/liked/{productId}")
    public String wantProduct(@PathVariable String productId, @RequestBody String userNo) {

//        log.info("temp = {}, {}", userNo, productId);
        System.out.println(userNo);
        return null;
    }

    // TODO 찜 상품 조회 API
//    @GetMapping("/products?liked?liked={boolean}&page={integer}&size={integer}")
}
