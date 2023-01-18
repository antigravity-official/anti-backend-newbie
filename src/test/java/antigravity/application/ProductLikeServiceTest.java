package antigravity.application;

import antigravity.application.dto.ProductLikeResponse;
import antigravity.common.exception.DuplicatedLikeException;
import antigravity.common.exception.ErrorMessage;
import antigravity.common.exception.NotFoundProductException;
import antigravity.common.exception.NotFoundUserException;
import antigravity.domain.repository.ProductLikeRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import static org.assertj.core.api.Assertions.*;


@SpringBootTest
@DirtiesContext
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
        productId = 10L;

        // when
        ProductLikeResponse productRegisterResponse = productLikeService.like(userId, productId);

        // then
        assertThat(productRegisterResponse.getProductId()).isEqualTo(productId);

    }

    @Test
    @DisplayName("이미 찜한 상품이라면, DuplicatedLikedException을 반환한다.")
    void likeTest_DuplicatedLiked_fail() {
        // given
        userId = 2L;
        productId = 5L;
        productLikeService.like(userId, productId);

        //when & then
        assertThatThrownBy(() -> productLikeService.like(userId, productId))
                .isInstanceOf(DuplicatedLikeException.class)
                .hasMessage(ErrorMessage.DUPLICATE_LIKE.getMessage());


    }

    @Test
    @DisplayName("찜하고자하는 유저의 아이디가 가입되어 있지 않거나, 탈퇴한 회원이라면 NotFoundUserException이 발생한다.")
    void likeTest_deleted_user_fail() {
        // given
        userId = 1L;
        productId = 9L;
        Long notRegisteredUserId = 999L;

        //when & then
        assertThatThrownBy(() -> productLikeService.like(userId, productId))
                .isInstanceOf(NotFoundUserException.class)
                .hasMessage(ErrorMessage.NOT_FOUND_USER.getMessage());

        assertThatThrownBy(() -> productLikeService.like(notRegisteredUserId, productId))
                .isInstanceOf(NotFoundUserException.class)
                .hasMessage(ErrorMessage.NOT_FOUND_USER.getMessage());

    }

    @Test
    @DisplayName("찜하고자하는 상품이 없다면, NotFoundProductException이 발생한다.")
    void likeTest_notRegistered_product_fail() {
        // given
        userId = 2L;
        productId = 999L;

        //when & then
        assertThatThrownBy(() -> productLikeService.like(userId, productId))
                .isInstanceOf(NotFoundProductException.class)
                .hasMessage(ErrorMessage.NOT_FOUND_PRODUCT.getMessage());

    }

}
