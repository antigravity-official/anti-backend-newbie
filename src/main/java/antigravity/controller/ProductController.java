package antigravity.controller;

import antigravity.config.PageParam;
import antigravity.payload.ProductResponse;
import antigravity.repository.ProductRepository;
import antigravity.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

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
        } else if (value.equals("401")) {
            return ResponseEntity.status(400).body("회원 정보가 존재하지 않습니다.");
        } else if (value.equals("402")) {
            return ResponseEntity.status(400).body("상품정보가 존재 하지 않습니다.");
        } else if (value.equals("506")) {
            return ResponseEntity.status(400).body("등록 중 오류가 발생 되었습니다.");
        }

        return ResponseEntity.status(201).body("찜 등록이 완료 되었습니다.");

    }

    // TODO 찜 상품 조회 API
    @GetMapping( )
    public ResponseEntity productView(@RequestParam(required = false) boolean liked,
                            @ModelAttribute PageParam pageParam,
                            HttpServletRequest request,
                            @RequestHeader("X-USER-ID") Long userId){

        List<ProductResponse> productResponseList = null;

        // liked 파라미터가 없는 경우
        if(request.getQueryString().indexOf("liked") != 0){
            productResponseList = productService.findAllProductList(pageParam,userId);

        }else if(!liked) {  //liked == false 이면 찜하지 않은 상품만 조회
            productResponseList = productService.favoriteProductList(pageParam,userId,liked);
        }else if(liked){
            // liked가 참인 경우
            productResponseList = productService.favoriteProductList(pageParam,userId, liked);
        }

        if(Objects.isNull(productResponseList)){
            return ResponseEntity.status(400).body("상품 조회중 문제가 발생되었습니다.");
        }

        return ResponseEntity.status(200).body(productResponseList);
    }

}
