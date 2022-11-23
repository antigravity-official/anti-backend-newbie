package antigravity.controller;


import antigravity.entity.Customer;
import antigravity.entity.Product;
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
    // TODO: 컨트롤러에서 DTO 를 조립하는 형식으로 Controller 의 덩치가 커짐
    // 나중에 서비스 코드로 옮기는 것도 고려해야됨.
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

        // 상품 기본정보를 조회한다.
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

        // 찜한 상품 여부를 업데이트 한다.
        // 아래로 뺀이유는 O(n^2) 을 만들지 않기 위해 따로 뺌.
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
}
