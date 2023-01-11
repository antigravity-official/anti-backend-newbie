package antigravity.product.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;

import antigravity.product.errors.CustomException;
import antigravity.product.errors.ErrorCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class LikeTest {

	@DisplayName("찜 등록 - 성공")
	@Test
	void likeProduct_success() {
		//given
		Like like = Like.builder()
			.liked(false)
			.build();

		Product product = mock(Product.class);

		//when
		like.likeProduct(product);

		//then
		assertThatThrownBy(() -> like.likeProduct(product))
			.isInstanceOf(CustomException.class)
			.hasMessage(ErrorCode.ALREADY_LIKED_PRODUCT.getMessage());
	}

	@DisplayName("찜 등록 - 실패 : 이미 찜이 되어있는 경우")
	@Test
	void likeProduct_failed_AlreadyLikedProduct() {
		//given
		Like like = Like.builder()
			.liked(true)
			.build();

		Product product = mock(Product.class);

		//when
		//then
		assertThatThrownBy(() -> like.likeProduct(product))
			.isInstanceOf(CustomException.class)
			.hasMessage(ErrorCode.ALREADY_LIKED_PRODUCT.getMessage());
	}
}