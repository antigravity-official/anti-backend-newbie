package antigravity.controller;

import antigravity.entity.Product;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/products")
public class ProductController {

    // TODO 찜 상품 등록 API
    @PostMapping("/liked/{productId}")
    public ResponseEntity likedCreate(@PathVariable int productId){
        System.out.println("produtID : " + productId);

        return ResponseEntity.ok().body("등록이 완료 되었습니다.");

    }


    // TODO 찜 상품 조회 API
    @GetMapping()
    public void productView(@RequestParam("liked") boolean liked,
                               @RequestParam("page") int page,
                               @RequestParam("size") int size){

        System.out.println("value : " + liked);
        System.out.println("page : " + page);
        System.out.println("size : " + size);
    }



}
