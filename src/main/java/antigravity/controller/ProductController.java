package antigravity.controller;


import antigravity.payload.PostResponse;
import antigravity.payload.ProductResponse;
import antigravity.service.ProductService;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    // TODO 찜 상품 조회 API
    @GetMapping()
    public List<ProductResponse> findAll(
            @RequestParam(value = "liked", required = false) String liked,
            @RequestParam("page") String page,
            @RequestParam("size") String size,
            @RequestHeader Map<String, String> requestHeader
    ) {
        long userId = Long.parseLong(requestHeader.get("x-user-id"));
        return productService.find(userId, liked, page, size);
    }

    // TODO 찜 상품 등록 API
    @PostMapping("/liked/{productId}")
    public ResponseEntity<PostResponse> like(@PathVariable String productId,
                                             @RequestHeader Map<String, String> requestHeader) {

        long userId = Long.parseLong(requestHeader.get("x-user-id"));
        return productService.like(userId, Long.parseLong(productId));


    }

}
