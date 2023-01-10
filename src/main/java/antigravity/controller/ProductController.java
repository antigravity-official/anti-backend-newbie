package antigravity.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import antigravity.payload.ProductResponse;
import antigravity.payload.Response;
import antigravity.service.ProductLikeService;
import antigravity.service.ProductService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/products")
@RestController
public class ProductController {

	public static final String USER_ID_HEADER_KEY = "X-USER-ID";

	private final ProductService productService;
	private final ProductLikeService productLikeService;

	@PostMapping("/liked/{productId}")
	public ResponseEntity<Response<String>> addLikedProduct(
		@PathVariable Long productId,
		@RequestHeader(USER_ID_HEADER_KEY) Integer userId) {

		productLikeService.productLike(productId, userId.longValue());
		return new ResponseEntity<>(Response.success("찜이 완료되었습니다."), HttpStatus.CREATED);
	}

	@GetMapping("/liked")
	public ResponseEntity<Response<Page<ProductResponse>>> getProducts(
		@RequestHeader(USER_ID_HEADER_KEY) Integer userId,
		@RequestParam(required = false) Boolean liked,
		@PageableDefault(sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {

		Page<ProductResponse> products = productService.getProducts(userId.longValue(), pageable, liked);

		return new ResponseEntity<>(Response.success(products), HttpStatus.OK);
	}

}
