package antigravity.controller;

import antigravity.payload.request.CreateRequest;
import antigravity.payload.request.SearchRequest;
import antigravity.payload.response.SearchResponse;
import antigravity.service.CreateLikedService;
import antigravity.service.SearchProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController()
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {
    private final CreateLikedService createLikedService;
    private final SearchProductService searchProductService;

    // TODO 찜 상품 등록 API
    @PostMapping("/liked/{productId}")
    public ResponseEntity<HttpStatus> createLiked(@RequestHeader(name = "X-USER-ID") Long userId,
                                                  @Valid CreateRequest createRequest) {
        createLikedService.createLiked(userId, createRequest.getProductId());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    // TODO 찜 상품 조회 API
    @GetMapping("/liked")
    public ResponseEntity<List<SearchResponse>> searchProduct(@RequestHeader(name = "X-USER-ID") Long userId,
                                                              SearchRequest searchRequest,
                                                              Pageable pageable) {
        List<SearchResponse> searchResponse = searchProductService.searchProduct(userId, searchRequest.getLiked(), pageable);
        return new ResponseEntity<>(searchResponse, HttpStatus.OK);
    }
}
