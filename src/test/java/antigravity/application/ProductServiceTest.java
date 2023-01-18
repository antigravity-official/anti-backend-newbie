package antigravity.application;

import antigravity.application.dto.ProductResponse;
import antigravity.infra.ProductJpaRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.DirtiesContext;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@DirtiesContext
class ProductServiceTest {

    @Autowired
    private ProductService productService;

    private Long userId;
    private Optional<Boolean> liked;
    private Pageable pageable;
    @Autowired
    private ProductJpaRepository productJpaRepository;

    @Test
    @DisplayName("Liked 조건이 null이라면, 전체 상품 페이징 조회를 반환한다.")
    void searchAnyProductsTest() {
        // given
        userId = 2L;
        liked = Optional.empty();
        pageable = PageRequest.of(0, 10);

        // when
        Page<ProductResponse> productResponses = productService.searchProducts(userId, pageable, liked);

        // then
        assertThat(productResponses.getSize()).isEqualTo(10);
        assertThat(productResponses.getNumberOfElements()).isEqualTo(10);
    }

    @Test
    @DisplayName("Liked 조건이 true라면, user가 찜한 상품의 페이징 조회를 반환한다.")
    void searchLikedProducts() {
        // given
        userId = 2L;
        liked = Optional.of(true);
        pageable = PageRequest.of(0, 10);

        // when
        Page<ProductResponse> productResponses = productService.searchProducts(userId, pageable, liked);

        // then
        assertThat(productResponses.getSize()).isEqualTo(10);
        assertThat(productResponses.getNumberOfElements()).isEqualTo(3);
        productResponses.get()
                .forEach(productResponse -> assertThat(productResponse.getLiked()).isTrue());
    }

    @Test
    @DisplayName("Liked 조건이 false라면, user가 찜하지않은 상품의 페이징 조회를 반환한다.")
    void searchNotLikedProducts() {
        // given
        userId = 2L;
        liked = Optional.of(false);
        pageable = PageRequest.of(0, 10);

        // when
        Page<ProductResponse> productResponses = productService.searchProducts(userId, pageable, liked);

        // then
        assertThat(productResponses.getSize()).isEqualTo(10);
        assertThat(productResponses.getNumberOfElements()).isEqualTo(10);
        productResponses.get()
                .forEach(productResponse -> assertThat(productResponse.getLiked()).isFalse());
    }
}
