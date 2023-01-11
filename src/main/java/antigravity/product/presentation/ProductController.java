package antigravity.product.presentation;

import antigravity.product.application.LikeProductService;
import antigravity.user.application.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/products")
@RestController
public class ProductController {

    private static final String X_USER_ID_HEADER = "X-USER-ID";

    private final UserService userService;
    private final LikeProductService likeProductService;

    @PostMapping("/liked/{productId}")
    @ResponseStatus(HttpStatus.CREATED)
    void likeProduct(@PathVariable Long productId, @RequestHeader(value = X_USER_ID_HEADER) Long userId) {
        userService.checkUserIsActive(userId);
        likeProductService.like(productId, userId);
    }
}
