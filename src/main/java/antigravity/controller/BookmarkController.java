package antigravity.controller;

import antigravity.payload.ProductResponse;
import antigravity.service.BookmarkService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/products")
public class BookmarkController {

    private final BookmarkService bookmarkService;

    // TODO 찜 상품 등록 API
    @PostMapping("/liked/{productId}")
    public ResponseEntity<Object> userBookmarkCreate(
            @RequestHeader("X-USER-ID") Integer userId,
            @PathVariable(value = "productId") Long productId){

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(bookmarkService.createUserBookmark(Long.valueOf(userId),productId));
    }

    // TODO 찜 상품 조회 API
    @GetMapping("/liked/{liked}")
    public ResponseEntity<Page<ProductResponse>> userBookmarkGet(
            @RequestHeader("X-USER-ID") Integer userId,
            @RequestParam(value = "liked", required = false) Boolean liked,
            Pageable pageable){

        return ResponseEntity
                .ok(bookmarkService.getUserBookmark(liked,pageable, Long.valueOf(userId)));
    }
}
