package antigravity.product.application.service;

import antigravity.product.application.port.in.dto.LikeProductInput;
import antigravity.product.application.port.in.LikeProductUseCase;
import antigravity.product.application.port.out.LoadLikePort;
import antigravity.product.application.port.out.LoadProductPort;
import antigravity.product.application.port.out.QueryUserPort;
import antigravity.product.domain.Like;
import antigravity.product.domain.Product;
import antigravity.product.errors.CustomException;
import antigravity.product.errors.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
class LikeProductService implements LikeProductUseCase {

	private final LoadProductPort loadProductPort;
	private final QueryUserPort queryUserPort;
	private final LoadLikePort loadLikePort;

	@Transactional
	@Override
	public void likeProduct(LikeProductInput input) {
		validateUserExists(input.getUserId());

		getProduct(input.getProductId()).addLike(getLike(input));
	}

	private void validateUserExists(Long userId) {
		if (!queryUserPort.existUser(userId)) {
			throw new CustomException(ErrorCode.NOT_EXISTS_USER);
		}
	}

	private Product getProduct(Long productId) {
		return loadProductPort.getProduct(productId)
			.orElseThrow(() -> new CustomException(ErrorCode.NOT_EXISTS_PRODUCT));
	}

	private Like getLike(LikeProductInput command) {
		return loadLikePort.getLike(command.getUserId(), command.getProductId())
			.orElse(Like.create(command.getUserId()));
	}

}
