package antigravity.product.repository;

import static org.assertj.core.api.Assertions.assertThat;

import antigravity.config.JpaConfig;
import antigravity.config.QueryDslConfig;
import antigravity.product.application.ProductQueryRepository;
import antigravity.product.domain.Product;
import antigravity.product.domain.ProductLike;
import antigravity.product.domain.ProductView;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

@Import({JpaConfig.class, QueryDslConfig.class, ProductQueryDslRepository.class})
@AutoConfigureTestDatabase(replace = Replace.NONE)
@DataJpaTest
class ProductQueryRepositoryImplTest {
    @Autowired
    private ProductJpaRepository productJpaRepository;

    @Autowired
    private ProductLikeJpaRepository productLikeJpaRepository;

    @Autowired
    private ProductViewJpaRepository productViewJpaRepository;

    @Autowired
    private ProductQueryDslRepository productQueryDslRepository;

    private ProductQueryRepository sut;


    private final Long userId = 1L;

    @BeforeEach
    void beforeEach() {
        List<Product> products = productJpaRepository.findAll().stream().filter(p -> !p.isDeleted())
                .collect(
                        Collectors.toList());
        for (int i = 0; i < products.size(); i+=2) {
            Product product = products.get(i);
            Long productId = product.getId();

            ProductView productView = ProductView.builder()
                    .productId(productId)
                    .build();
            productViewJpaRepository.save(productView);

            ProductLike productLike = ProductLike.builder()
                    .productId(productId)
                    .userId(userId)
                    .build();
            productLikeJpaRepository.save(productLike);
            sut = new ProductQueryRepositoryImpl(productQueryDslRepository);
        }
    }

    @Test
    @DisplayName("모든 상품 목록을 조회할 수 있다.")
    void givenLikedIsNull_whenSearch_thenAllProducts() {
        //given
        Boolean liked = null;
        Integer page = 1;
        Integer size = 10;

        //when
        SearchResponse<ProductResponse> result = sut.search(liked, userId, page, size);

        assertThat(result.getCurrentPage()).isEqualTo(1);
        assertThat(result.getCurrentPage()).isEqualTo(page);
        assertThat(result.getSize()).isEqualTo(size);
        assertThat(result.getContent().size()).isEqualTo(10);
        assertThat(result.getContent().get(0).getLiked()).isFalse();
    }

    @Test
    @DisplayName("내가 찜한 상품 목록을 조회할 수 있다.")
    void givenLikedIsTrue_whenSearch_thenProductsWithILiked() {
        //given
        Boolean liked = true;
        Integer page = 1;
        Integer size = 10;

        //when
        SearchResponse<ProductResponse> result = sut.search(liked, userId, page, size);

        assertThat(result.getCurrentPage()).isEqualTo(1);
        assertThat(result.getContent().stream().allMatch(i -> i.getLiked().equals(true))).isTrue();
    }

    @Test
    @DisplayName("내가 찜하지 않은 상품 목록을 조회할 수 있다.")
    void givenLikedIsFalse_whenSearch_thenProductsWithIDidNotLiked() {
        //given
        Boolean liked = false;
        Integer page = 1;
        Integer size = 10;

        //when
        SearchResponse<ProductResponse> result = sut.search(liked, userId, page, size);

        assertThat(result.getCurrentPage()).isEqualTo(1);
        assertThat(result.getContent().stream().noneMatch(i -> i.getLiked().equals(true))).isTrue();
    }
}