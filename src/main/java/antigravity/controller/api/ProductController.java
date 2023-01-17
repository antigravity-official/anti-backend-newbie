package antigravity.controller.api;


import antigravity.payload.APIDataResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@RequestMapping("/products")
@Controller
public class ProductController {

    // TODO: 찜 상품 등록 API
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/liked/{productId}")
    public APIDataResponse<String> productRegistration(
            @PathVariable("productId")Long id
    ) {
        boolean result = productService.createEvent();
        return APIDataResponse.of(Boolean.toString(result));
    }



    // TODO: 찜 상품 조회 API

}
