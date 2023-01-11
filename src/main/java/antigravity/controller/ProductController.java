package antigravity.controller;

import antigravity.payload.ResponseDto;
import antigravity.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;





@RequiredArgsConstructor
@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;


    // TODO 찜 상품 등록 API
    @PostMapping(value = "/liked/{productId}")
    public ResponseDto<?> likeProduct(@PathVariable Long productId,@RequestHeader("X-USER-ID") Long memberId)  {
        return productService.likeProduct(productId,memberId);
    }



    // TODO 찜 상품 조회 API

    @GetMapping("/liked")
    public ResponseDto<?> getLikeProduct(@RequestParam(required = false) Boolean liked,
                                         @PageableDefault(sort = "updatedAt", direction = Sort.Direction.DESC) Pageable pageable,@RequestHeader("X-USER-ID") Long memberId)  {
        return productService.getLikeProduct(liked,pageable,memberId);
    }



}
