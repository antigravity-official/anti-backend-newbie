package antigravity.controller.api;


import antigravity.payload.APIDataResponse;
import antigravity.payload.ProductResponse;
import antigravity.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/products")
@RestController
public class ProductController {

    private final ProductService productService;


    // TODO: 찜 상품 등록 API
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/liked/{productId}")
    public APIDataResponse<String> createEvent(@PathVariable("productId") Long productId) {
        boolean result = productService.insertProductInBasket(productId);
        return APIDataResponse.of(Boolean.toString(result));
    }



    // TODO: 찜 상품 조회 API
    @GetMapping("/liked")
    public APIDataResponse<ProductResponse> basketInProudct(
            @RequestParam(value = "offset", required = false, defaultValue = "0") Long offset,
            @RequestParam(value = "size", required = false, defaultValue = "100") Long size
    ) {
        //ProductResponse productResponse = ProductResponse.from(productService.getBasket(eventId).orElse(null));

        return APIDataResponse.of(null);
    }

}
