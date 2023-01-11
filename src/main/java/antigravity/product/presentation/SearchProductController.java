package antigravity.product.presentation;

import antigravity.product.application.SearchProductService;
import antigravity.product.repository.ProductResponse;
import antigravity.product.repository.SearchResponse;
import antigravity.user.application.UserService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/products")
@RestController
public class SearchProductController {
    private static final String X_USER_ID_HEADER = "X-USER-ID";
    private final UserService userService;
    private final SearchProductService searchProductService;

    @GetMapping("/liked")
    SearchResponse<ProductResponse> searchProductWithUser(
            @RequestHeader(value = X_USER_ID_HEADER) Long userId,
            @Valid @ModelAttribute SearchProductRequest searchProductRequest) {
        userService.checkUserIsActive(userId);
        return searchProductService.searchProduct(searchProductRequest.getLiked(), userId,
                searchProductRequest.getPage(), searchProductRequest.getSize());
    }
}
