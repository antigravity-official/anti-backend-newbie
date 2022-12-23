package antigravity.controller;

import antigravity.entity.LikedStatus;
import antigravity.entity.dto.LikedDto;
import antigravity.entity.dto.LikedDto.Create;
import antigravity.service.LikedCreator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/products")
public class ProductController {

    private final LikedCreator likedCreator;

    // 찜 상품 등록 API
    @PostMapping("/liked/{productId}")
    public ResponseEntity<Create.Response> createLiked(
            @RequestHeader("X-USER-ID") Integer memberId,
            @PathVariable Long productId,
            @RequestBody(required = false) LikedDto.Create.Request request) {

        Create.Response result = likedCreator.create(new Create.Condition(productId,
                                                                          memberId.longValue(),
                                                                          request
        ));
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    // TODO 찜 상품 조회 API

}
