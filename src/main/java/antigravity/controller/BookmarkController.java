package antigravity.controller;

import antigravity.entity.Bookmark;
import antigravity.service.BookmarkService;
import lombok.RequiredArgsConstructor;
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



}
