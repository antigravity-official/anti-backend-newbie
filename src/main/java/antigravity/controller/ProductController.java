package antigravity.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import antigravity.payload.LikedProductResponse;
import antigravity.payload.ProductResponse;
import antigravity.payload.ProductSearch;
import antigravity.service.ProductService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {

	private final ProductService productService;

	// TODO 찜 상품 등록 API
	@PostMapping("/liked/{productId}")
	public ResponseEntity<LikedProductResponse> likedProduct(
			@PathVariable Long productId,
			@RequestHeader("X-USER-ID") Integer userId) {
		return productService.registerLikedProduct(productId, userId.longValue());
	}
	// TODO 찜 상품 조회 API

	@GetMapping
	public ResponseEntity<List<ProductResponse>> getProducts(
			@RequestHeader("X-USER-ID") Integer userId,
			@ModelAttribute ProductSearch productSearch) {

		return productService.findProducts(userId.longValue(), productSearch);
	}
}
