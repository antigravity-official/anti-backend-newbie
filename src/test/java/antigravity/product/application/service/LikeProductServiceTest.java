package antigravity.product.application.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

import antigravity.product.application.port.in.dto.LikeProductInput;
import antigravity.product.application.port.out.LoadLikePort;
import antigravity.product.application.port.out.LoadProductPort;
import antigravity.product.application.port.out.QueryUserPort;
import antigravity.product.domain.Like;
import antigravity.product.domain.Product;
import antigravity.product.errors.CustomException;
import antigravity.product.errors.ErrorCode;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class LikeProductServiceTest {

	@Mock
	private LoadProductPort loadProductPort;
	@Mock
	private QueryUserPort queryUserPort;
	@Mock
	private LoadLikePort loadLikePort;
	@InjectMocks
	LikeProductService likeProductService;


	@DisplayName("찜 등록 - 성공")
	@Test
	void likeProduct_success() {
		//given
		Long userId = 1L;
		Long productId = 1L;

		LikeProductInput input = new LikeProductInput(userId, productId);

		Product product
			= mock(Product.class);
		Like like
			= mock(Like.class);

		given(queryUserPort.existUser(anyLong()))
			.willReturn(true);
		given(loadProductPort.getProduct(productId))
			.willReturn(Optional.of(product));
		given(loadLikePort.getLike(userId, productId))
			.willReturn(Optional.of(like));

		//when
		//then
		assertDoesNotThrow(() -> likeProductService.likeProduct(input));
	}

	@DisplayName("찜 등록 - 실패 : 회원이 존재하지 않음")
	@Test
	void likeProduct_failed_NotExistUser(){
	    //given
		Long userId = 1L;
		Long productId = 1L;

		LikeProductInput input = new LikeProductInput(userId, productId);

		given(queryUserPort.existUser(anyLong()))
			.willReturn(false);

		//when
		//then
		assertThatThrownBy(() -> likeProductService.likeProduct(input))
			.isInstanceOf(CustomException.class)
			.hasMessage(ErrorCode.NOT_EXISTS_USER.getMessage());
	}

	@DisplayName("찜 등록 - 실패 : 상품이 존재하지 않음")
	@Test
	void likeProduct_failed_NotExistProduct(){
		//given
		Long userId = 1L;
		Long productId = 1L;

		LikeProductInput input = new LikeProductInput(userId, productId);

		given(queryUserPort.existUser(anyLong()))
			.willReturn(true);
		given(loadProductPort.getProduct(productId))
			.willReturn(Optional.empty());

		//when
		//then
		assertThatThrownBy(() -> likeProductService.likeProduct(input))
			.isInstanceOf(CustomException.class)
			.hasMessage(ErrorCode.NOT_EXISTS_PRODUCT.getMessage());
	}

	/**
	 * 이미 찜이 등록되어있는 경우는 도메인 유효성 검증 로직이기 때문에 도메인에서 처리
	 */
}