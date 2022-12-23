package antigravity.controller;

import antigravity.payload.LikedDto;
import antigravity.payload.LikedDto.Create;
import antigravity.payload.LikedDto.Retrieve;
import antigravity.payload.ProductResponse;
import antigravity.service.LikedCreator;
import antigravity.service.LikedRetriever;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static antigravity.payload.ProductConstants.SORT_PROPERTY;
import static antigravity.payload.ProductConstants.X_USER_ID_KEY;
import static org.springframework.data.domain.Sort.Direction.DESC;

@RequiredArgsConstructor
@RestController
@RequestMapping("/products")
public class ProductController {

    private final LikedCreator likedCreator;
    private final LikedRetriever likedRetriever;

    // 찜 상품 등록 API
    @PostMapping("/liked/{productId}")
    public ResponseEntity<Create.Response> createLiked(
            @RequestHeader(X_USER_ID_KEY) Integer memberId,
            @PathVariable Long productId,
            @RequestBody(required = false) LikedDto.Create.Request request) {
        Create.Response result = likedCreator.create(new Create.Condition(productId,
                                                                          memberId.longValue(),
                                                                          request
        ));
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    // 찜 상품 조회 API
    @GetMapping("/liked")
    public ResponseEntity<Page<ProductResponse>> getProducts(
            @RequestHeader(X_USER_ID_KEY) Integer memberId,
            @RequestParam(required = false) Boolean liked,
            @PageableDefault(sort = SORT_PROPERTY, direction = DESC) Pageable pageable) {
        return new ResponseEntity<>(likedRetriever.products(new Retrieve.Condition(memberId.longValue(), liked, pageable)),
                                    HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Page<ProductResponse>> getAllProducts(
            @RequestHeader(X_USER_ID_KEY) Integer memberId,
            @PageableDefault(sort = SORT_PROPERTY, direction = DESC) Pageable pageable) {

        return new ResponseEntity<>(likedRetriever.retrieveAll(memberId.longValue(), pageable),
                                    HttpStatus.OK);
    }


}
