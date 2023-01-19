package antigravity.controller.api;


import antigravity.entity.User;
import antigravity.payload.APIDataResponse;
import antigravity.payload.ProductResponse;
import antigravity.service.ProductInfoService;
import antigravity.service.ProductRequestService;
import antigravity.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.Parameter;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

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
    public APIDataResponse<String> createEvent(@PathVariable("productId") Long productId,
                                               @RequestHeader("X-USER-ID") Integer userId) {

        boolean basketResult = productService.insertProductInBasket(productId, userId);
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

        return APIDataResponse.of(productRequestService.getProducts(liked, page, size, userId));
    }

}
