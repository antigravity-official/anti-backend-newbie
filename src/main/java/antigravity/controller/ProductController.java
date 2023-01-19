package antigravity.controller;

import antigravity.exception.CMResDto;
import antigravity.exception.CustomException;
import antigravity.payload.ProductResponse;
import antigravity.service.LikedService;
import antigravity.service.ProductService;
import antigravity.service.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class ProductController {

    private final ProductService productService;
    private final LikedService likedService;
    private final UserService userService;

    // TODO 찜 상품 등록 API
    @PostMapping("/products/liked/{productId}")
    public ResponseEntity<CMResDto<?>> postLiked(@PageableDefault(size=8, sort="id",direction = Sort.Direction.DESC) Pageable pageable,
                                              HttpServletRequest request, @PathVariable Long productId) {

        Long userId = Long.parseLong(request.getHeader("X-USER-ID"));
        //user,product,찜여부 확인 후 찜 등록
        userService.getUser(userId);
        productService.getProduct(productId);
        likedService.postLiked(userId,productId);
        //product 조회수 및 찜횟수 증가
        productService.increaseViewAndTotalLiked(productId);
        return new ResponseEntity<>(new CMResDto<>("201 created","찜이 등록되었습니다.",null), HttpStatus.CREATED);
    }

    // TODO 찜 상품 조회 API
    @GetMapping("/products/liked")
    public ResponseEntity<CMResDto<?>> getProducts(@RequestParam(required = false) String liked,
                                                   Pageable pageable,HttpServletRequest request) {

        Long userId = Long.parseLong(request.getHeader("X-USER-ID"));
        return productService.getLikedProductList(liked, pageable, userId);
    }

}
