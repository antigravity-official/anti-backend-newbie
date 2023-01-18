package antigravity.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import antigravity.common.ApiResponse;
import antigravity.config.UserId;
import antigravity.payload.CartResponse;
import antigravity.service.CartService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/api/products")
@RestController
public class ProductController {

	private final CartService cartService;

	// TODO 찜 상품 등록 API
	@ResponseStatus(code = HttpStatus.CREATED)
	@PostMapping("/liked/{productId}")
	public ApiResponse<CartResponse> like(@UserId Integer userId, @PathVariable Long productId) {
		CartResponse cartResponse = cartService.like(Long.valueOf(userId), productId);

		return new ApiResponse<>(cartResponse);
	}
}
