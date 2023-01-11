package antigravity.product.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import antigravity.config.JpaConfig;
import antigravity.product.domain.Product;
import antigravity.product.domain.ProductLike;
import antigravity.product.domain.ProductView;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

@Import(JpaConfig.class)
@AutoConfigureTestDatabase(replace = Replace.NONE)
@DataJpaTest
class ProductRepositoryImplTest {

    @Autowired
    private ProductJpaRepository productJpaRepository;

    @Autowired
    private ProductLikeJpaRepository productLikeJpaRepository;

    @Autowired
    private ProductViewJpaRepository productViewJpaRepository;

    @Test
    @DisplayName("productId로 Product를 찾을 수 있다.")
    void givenProductId_whenGetById_thenProductFound() {
        //given
        Long productId = 1L;

        //when
        ProductRepositoryImpl sut = new ProductRepositoryImpl(productJpaRepository,
                productLikeJpaRepository,
                productViewJpaRepository);
        Product product = sut.getById(productId);

        //then
        assertThat(product.getId()).isEqualTo(productId);
    }

    @Test
    @DisplayName("productId에 해당하는 Product가 없을 때 예외를 던진다.")
    void whenNotFoundProductByProductId_thenExceptionShouldBeThrown() {
        //given
        Long productId = 0L;

        //when
        ProductRepositoryImpl sut = new ProductRepositoryImpl(productJpaRepository,
                productLikeJpaRepository,
                productViewJpaRepository);

        //then
        assertThatThrownBy(() -> sut.getById(productId))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("상품을 찾을 수 없습니다.");
    }

    @Test
    @DisplayName("productId로 해당 Product의 조회수를 증가할 수 있다.")
    void givenValidProductId_whenIncreseViewsByProductId_thenSuccess() {
        //given
        Long productId = 1L;

        //when
        ProductRepositoryImpl sut = new ProductRepositoryImpl(productJpaRepository,
                productLikeJpaRepository,
                productViewJpaRepository);
        sut.increseViewsByProductId(productId);

        //then
        List<ProductView> productViews = productViewJpaRepository.findAll();
        assertThat(productViews.size()).isEqualTo(1L);
        ProductView productView = productViews.get(0);
        assertThat(productView.getProductId()).isEqualTo(productId);
    }

    @Test
    @DisplayName("productId와 userId로 ProductLike를 만들 수 있다.")
    void givenProductIdAndUserId_whenLike_thenProductLikeMade() {
        //given
        Long productId = 1L;
        Long userId = 1L;

        //when
        ProductRepositoryImpl sut = new ProductRepositoryImpl(productJpaRepository,
                productLikeJpaRepository,
                productViewJpaRepository);
        sut.like(productId, userId);

        //then
        List<ProductLike> productLikes = productLikeJpaRepository.findAll();
        assertThat(productLikes.size()).isEqualTo(1L);
        ProductLike productLike = productLikes.get(0);
        assertThat(productLike.getProductId()).isEqualTo(productId);
        assertThat(productLike.getUserId()).isEqualTo(userId);
    }

    @Test
    @DisplayName("해당 productId와 userId를 가진 ProductLike가 존재한다.")
    void givenExistsByProductIdAndUserId_whenLike_thenExceptionShouldBeThrown() {
        //given
        Long productId = 1L;
        Long userId = 1L;
        ProductRepositoryImpl sut = new ProductRepositoryImpl(productJpaRepository,
                productLikeJpaRepository,
                productViewJpaRepository);
        sut.like(productId, userId);

        //expected
        assertThatThrownBy(() -> sut.like(productId, userId))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("이미 찜한 상품입니다.");
    }
}