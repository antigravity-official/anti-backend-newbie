package antigravity.controller;

import antigravity.Service.WishService;
import antigravity.payload.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class WishController {
    private final WishService wishService;

    @PostMapping("/products/liked/{productId}")
    public ResponseEntity<ResponseDto> CreateWish(@PathVariable Long productId, @RequestHeader(name = "X-USER-ID") Long userId) {
        return wishService.createLikedItems(productId,userId);
    }

}
