package antigravity.controller;

import antigravity.entity.Product;
import antigravity.repository.ProductRepository;
import antigravity.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.gson.GsonProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.ResourceBundle;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;
    private final ProductRepository productRepository;

    // TODO 찜 상품 등록 API
    @PostMapping("/liked/{productId}")
    public ResponseEntity likedCreate(@RequestHeader("X-USER-ID") Long userId ,@PathVariable Long productId){
        if(userId == null){
            return ResponseEntity.status(400).body("로그인 후 사용해주세요");
        }

        if(productId == null ){
            return ResponseEntity.status(400).body("상품 정보가 존재하지 않습니다.");
        }

        // 찜 등록
        String value = productService.createFavorite(userId, productId);

        if(value.equals("400")){
            return ResponseEntity.status(400).body("이미 찜을 누른 상품 입니다.");
        } else if (value.equals("506")) {
            return ResponseEntity.status(400).body("등록 중 오류가 발생 되었습니다.");
        }

        return ResponseEntity.status(201).body("찜 등록이 완료 되었습니다.");

    }






}
