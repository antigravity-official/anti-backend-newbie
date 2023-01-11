package antigravity.product.application;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;

import antigravity.product.domain.ProductLike;
import antigravity.product.domain.ProductView;
import antigravity.product.repository.FakeProductRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class LikeProductServiceTest {
    private LikeProductService sut;

    private ProductRepository productRepository;

    @BeforeEach
    void beforeEach() {
        productRepository = new FakeProductRepository();
        sut = new LikeProductService(productRepository);
    }

    @Test
    @DisplayName("상품을 찜 등록할 수 있다.")
    void whenLikeProduct_thenSuccess() {
        //given
        Long productId = 1L;
        Long userId = 1L;

        //when
        sut.like(productId, userId);

        //then
        List<ProductView> productViews = ((FakeProductRepository) productRepository).getProductViews()
                .stream().filter(pv -> pv.getProductId().equals(productId)).collect(
                        Collectors.toList());
        assertEquals(1L, productViews.size());

        List<ProductLike> productLikes =
                ((FakeProductRepository) productRepository).getProductLikes();
        assertEquals(1L, productLikes.size());
        ProductLike productLike = productLikes.get(0);
        assertEquals(productId, productLike.getProductId());
        assertEquals(userId, productLike.getUserId());
    }

    @Test
    @DisplayName("존재하지 않는 상품을 찜 등록할 수 없다.")
    void givenNotFoundProductByProductId_whenLikeProduct_thenExceptionShouldBeThrown() {
        //given
        Long productId = 999L;
        Long userId = 1L;

        //expected
        assertThatThrownBy(() -> sut.like(productId, userId))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("상품을 찾을 수 없습니다.");
    }

    @Test
    @DisplayName("삭제된 상품을 찜할 수 없다.")
    void givenDeletedProductId_whenLikeProduct_thenExceptionShouldBeThrown() {
        //given
        Long productId = 21L;
        Long userId = 1L;

        //expected
        assertThatThrownBy(() -> sut.like(productId, userId))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("삭제된 상품입니다.");
    }

    @Test
    @DisplayName("이미 찜한 상품을 다시 찜할 수 없다.")
    void givenAlreadyLikedProductIdAndUserId_whenLikeProduct_thenExceptionShouldBeThrown() {
        //given
        Long productId = 1L;
        Long userId = 1L;
        sut.like(productId, userId);

        //expected
        assertThatThrownBy(() -> sut.like(productId, userId))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("이미 찜한 상품입니다.");

        List<ProductView> productViews = ((FakeProductRepository) productRepository).getProductViews()
                .stream().filter(pv -> pv.getProductId().equals(productId)).collect(
                        Collectors.toList());
        assertEquals(2L, productViews.size());

    }
}