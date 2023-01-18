package antigravity.application;

import antigravity.common.exception.ErrorMessage;
import antigravity.common.exception.NotFoundProductException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DirtiesContext
class ProductViewCacheManagerTest {

    @Autowired
    private ProductViewCacheManager productViewCacheManager;

    private Long productId;
    @Test
    @DisplayName("존재하는 상품이라면 viewCount를 반환한다.")
    void getViewCountTest_success() {
        productId = 2L;
        Integer viewCount = productViewCacheManager.getViewCount(productId);
        assertThat(viewCount).isZero();
    }

    @Test
    @DisplayName("존재하지 않는 상품이라면 NotFoundProductException을 반환한다.")
    void getViewCountTest_fail() {
        productId = 999L;

        assertThatThrownBy(() -> productViewCacheManager.getViewCount(productId))
                .isInstanceOf(NotFoundProductException.class)
                .hasMessage(ErrorMessage.NOT_FOUND_PRODUCT.getMessage());

    }

    @Test
    @DisplayName("productView count를 1 증가시킨다.")
    void incrementProductViewCountTest() {
        productId = 3L;

        Long viewCount = productViewCacheManager.incrementProductViewCount(productId);

        assertThat(productViewCacheManager.getViewCount(productId)).isEqualTo(viewCount.intValue());

    }
}
