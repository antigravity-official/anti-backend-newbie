package antigravity.application;

import antigravity.application.dto.ProductRegisterResponse;
import antigravity.common.exception.DuplicatedLikeException;
import antigravity.domain.repository.ProductLikeRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.*;


@SpringBootTest
class ProductLikeServiceTest {

    @Autowired
    private ProductLikeService productLikeService;

    @Autowired
    private ProductLikeRepository productLikeRepository;

    private Long userId;
    private Long productId;

    @Test
    @DisplayName("찜 등록을 성공하면, ProductRegisterResponse를 반환한다.")
    void like_success() {
        // given
        userId = 2L;
        productId = 1L;

        // when
        ProductRegisterResponse productRegisterResponse = productLikeService.like(userId, productId);

        // then
        assertThat(productRegisterResponse.getId()).isEqualTo(productId);

    }

    @Test
    @DisplayName("이미 찜한 상품이라면, DuplicatedLikedException을 반환한다.")
    void like() {
        // given
        userId = 2L;
        productId = 1L;
        productLikeService.like(userId, productId);

        //when & then
        assertThatThrownBy(() -> productLikeService.like(userId, productId))
                .isInstanceOf(DuplicatedLikeException.class)
                .hasMessage("이미 찜한 상품입니다.");


    }
}
