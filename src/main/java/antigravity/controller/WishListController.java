package antigravity.controller;

import antigravity.payload.CreateWishListRequest;
import antigravity.payload.ProductResponse;
import antigravity.repository.WishListRepositoryCustom;
import antigravity.service.WishListService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "{userId}")
public class WishListController {

    private final WishListService wishListService;

    private final WishListRepositoryCustom wishListRepositoryCustom;

    // TODO 찜 상품 등록 API
    @PostMapping("/products/liked/{productId}")
    public Long post(@RequestBody CreateWishListRequest request) {
        return wishListService.save(request);
    }

    // TODO 찜 상품 조회 API
    @GetMapping("/products")
    public Page<ProductResponse> getList(@PathVariable String userId,
                                         @RequestParam boolean liked,
                                         Pageable pageable) {
        return wishListService.getPage(userId, liked, pageable);
    }

}
