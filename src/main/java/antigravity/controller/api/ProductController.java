package antigravity.controller.api;


import antigravity.payload.APIDataResponse;
import antigravity.payload.ProductResponse;
import antigravity.service.ProductInfoService;
import antigravity.service.ProductRequestService;
import antigravity.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RequestMapping("/products")
@RestController
public class ProductController {

    private final ProductService productService;
    private final ProductRequestService productRequestService;
    private final ProductInfoService productInfoService;


    // TODO: 찜 상품 등록 API
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/liked/{productId}")
    public APIDataResponse<String> createEvent(@PathVariable("productId") Long productId) {
        boolean basketResult = productService.insertProductInBasket(productId);
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
            @Nullable  @RequestParam(value = "liked", required = false, defaultValue = "true") Boolean liked,
            @RequestParam(value = "page", required = false, defaultValue = "100") Integer page,
            @RequestParam(value = "size", required = false, defaultValue = "100") Integer size
    ) {

        return APIDataResponse.of(productRequestService.getProducts(liked, page, size));
    }

}
