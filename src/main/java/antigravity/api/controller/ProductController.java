package antigravity.api.controller;


import antigravity.api.service.ProductLikeService;
import antigravity.api.service.ProductSearchService;
import antigravity.payload.request.ProductLikedRequest;
import antigravity.payload.request.ProductSearchRequest;
import antigravity.payload.response.ProductSearchResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {

    private final ProductLikeService productLikeService;
    private final ProductSearchService productSearchService;

    /**
     * 과제 1. 찜 상품 등록 API
     *
     * @param userId
     * @param productId
     * @return User or Product Not Found -> 400 Bad Request, else 201 Created
     */
    @PostMapping("/liked/{productId}")
    public ResponseEntity<HttpStatus> likedProductAdd(@RequestHeader(name = "X-USER-ID") Long userId,
                                                      @Valid ProductLikedRequest productLikedRequest) {
        productLikeService.addLikedProduct(userId, productLikedRequest.getProductId());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /**
     * 과제 2. 찜 상품 조회 API
     *
     * @param userId
     * @param productSearchRequest
     * @return Invalidation Parameter -> 400 Bad Request, else 200 OK
     */
    @GetMapping
    public ResponseEntity<List<ProductSearchResponse>> likedProductSearch(@RequestHeader(name = "X-USER-ID") Long userId,
                                                                          @Valid ProductSearchRequest productSearchRequest,
                                                                          Pageable pageable) {
        productSearchService.searchLikedProduct(userId, productSearchRequest.getLiked(), pageable);
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);
    }
}
