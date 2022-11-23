package antigravity.controller;


import antigravity.entity.Customer;
import antigravity.entity.Product;
import antigravity.exception.CustomException;
import antigravity.exception.ErrorCode;
import antigravity.payload.ProductResponse;
import antigravity.service.ProductService;
import antigravity.service.ProductStatisticsService;
import antigravity.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final ProductStatisticsService productStatisticsService;
    private final UserService userService;
    private final ModelMapper modelMapper;

    // TODO 찜 상품 등록 API
    @PostMapping("/products/liked/{productId}")
    public ResponseEntity saveLikedProduct(
             @PathVariable Integer productId,
             @RequestHeader("X-USER-ID") Integer userId
    ) {
        productStatisticsService.increaseViewCount(productId.longValue());
        productService.registerLikeProduct(productId.longValue(), userId.longValue());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    // TODO 찜 상품 조회 API
    @GetMapping("/products/liked")
    public ResponseEntity<List<ProductResponse>> findByLikedStatus(
            @RequestParam(required = false) Boolean liked,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size,
            @NotNull @RequestHeader("X-USER-ID") Integer userId
    ){
        List<ProductResponse> productResponses = new ArrayList<>();
        List<Product> foundProducts = productService.findByLikedStatus(liked, userId.longValue(), page, size);
        Customer customer = userService.findById(userId.longValue());

        for (Product product : foundProducts) {
            ProductResponse productResponse = modelMapper.map(product, ProductResponse.class);
            Integer viewCount = 0;
            if (product.getProductStatistics() != null) {
                viewCount = product.getProductStatistics().getViewCount();
            }
            productResponse.setViewed(viewCount);
            productResponse.setTotalLiked(product.countLike());
            productResponses.add(productResponse);
        }

        for (ProductResponse productRespons : productResponses) {
            if (liked != null) {
                productRespons.setLiked(liked);
            } else {
                boolean likeProduct = userService.isLikeProduct(userId.longValue(), productRespons.getId());
                productRespons.setLiked(likeProduct);
            }
        }

        return new ResponseEntity<>(productResponses, HttpStatus.OK);
    }

    @GetMapping("ex")
    public void test () {
        throw new CustomException(ErrorCode.PRODUCT_NOT_FOUND);
    }
}
