package antigravity.product.adapter.in.web;

import static org.springframework.http.HttpStatus.CREATED;
import antigravity.product.application.port.in.dto.LikeProductInput;
import antigravity.product.application.port.in.LikeProductUseCase;
import antigravity.product.common.Response;
import javax.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/products")
@RestController
public class ProductController {

	private final LikeProductUseCase likeProductUseCase;

	// TODO 찜 상품 등록 API
	@PostMapping("/liked/{productId}")
	public ResponseEntity likeProduct(
		@RequestHeader("X-USER-ID") @NotNull Long userId,
		@PathVariable("productId") @NotNull Long productId
	) {
		likeProductUseCase.likeProduct(new LikeProductInput(userId, productId));
		return ResponseEntity.status(CREATED).body(Response.success("상품을 찜했습니다."));
	}

	// TODO 찜 상품 조회 API

}
