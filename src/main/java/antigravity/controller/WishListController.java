package antigravity.controller;

import antigravity.payload.CreateWishListRequest;
import antigravity.payload.ProductResponse;
import antigravity.service.WishListService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "{userId}")
public class WishListController {

    private final WishListService wishListService;

    // TODO 찜 상품 등록 API
    @PostMapping("/products/liked/{productId}")
    public Long post(@RequestBody @Valid CreateWishListRequest request) {
        return wishListService.save(request);
    }
    // TODO 찜 상품 조회 API
    @GetMapping("/products")
    public Page<ProductResponse> getList() {
        return wishListService.getPage();
    }

}
