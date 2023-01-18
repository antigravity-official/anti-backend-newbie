package antigravity.domain;

import antigravity.common.exception.DuplicatedLikeException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;


class ProductLikeTest {

    private Product product;
    private User user;
    private ProductLike productLike;

    @Test
    @DisplayName("deletedAt이 null인 ProductLike를 recoverLiked()하면 DuplicatedLikeException이 발생한다.")
    void recoverLikedTest_fail() {
        productLike = ProductLike.of(product, user);

        assertThatThrownBy(() -> productLike.recoverLiked())
                .isInstanceOf(DuplicatedLikeException.class)
                .hasMessage("이미 찜한 상품입니다.");
    }

}
