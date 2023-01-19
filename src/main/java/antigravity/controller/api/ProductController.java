package antigravity.controller.api;


import antigravity.payload.APIDataResponse;
import antigravity.payload.ProductResponse;
import antigravity.service.ProductInfoService;
import antigravity.service.ProductResponseService;
import antigravity.service.ProductRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/products")
@RestController
public class ProductController {

    private final ProductRequestService productRequestService;
    private final ProductResponseService productResponseService;
    private final ProductInfoService productInfoService;


    // TODO: 찜 상품 등록 API
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/liked/{productId}")
    public APIDataResponse<String> createEvent(@PathVariable("productId") Long productId,
                                               @RequestHeader("X-USER-ID") Integer userId) {

        boolean basketResult = productRequestService.insertProductInBasket(productId, userId);
        boolean productInfoResult = productInfoService.changeViewProduct(productId);
        boolean result = false;

        // TODO : true & true 수정
        if (basketResult == true && productInfoResult == true)
            result = true;
        return APIDataResponse.of(Boolean.toString(result));
    }



    // TODO: 찜 상품 조회 API
    @GetMapping
    public APIDataResponse<List<ProductResponse>> basketInProudct(
            @Nullable  @RequestParam(value = "liked", required = false, defaultValue = "") Boolean liked,
            @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
            @RequestParam(value = "size", required = false, defaultValue = "20") Integer size,
            @RequestHeader("X-USER-ID") Integer userId
    ) {

        return APIDataResponse.of(productResponseService.getProducts(liked, page, size, userId));
    }

}