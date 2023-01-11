package antigravity.product.application;

import static org.assertj.core.api.Assertions.assertThat;

import antigravity.product.domain.Product;
import antigravity.product.repository.FakeProductRepository;
import antigravity.product.repository.ProductResponse;
import antigravity.product.repository.SearchResponse;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class SearchProductServiceTest {

    private SearchProductService sut;

    private FakeProductRepository productRepository;

    private Long userId = 1L;

    @BeforeEach
    void beforeEach() {
        productRepository = new FakeProductRepository();
        List<Product> products = productRepository.getProducts().stream()
                .filter(p -> !p.isDeleted()).collect(
                        Collectors.toList());
        for (int i = 0; i < products.size(); i+=2) {
            Product product = products.get(i);
            Long productId = product.getId();
            productRepository.increseViewsByProductId(productId);
            productRepository.like(productId, userId);
        }

        sut = new SearchProductService(productRepository);
    }

    @Test
    @DisplayName("상품 목록을 조회할 수 있다.")
    void whenSearchProduct_thenAllProducts() {
        //given
        Boolean liked = null;
        Integer page = 2;
        Integer size = 10;

        //when
        SearchResponse<ProductResponse> response = sut.searchProduct(liked, userId,
                page, size);

        assertThat(response.getSize()).isEqualTo(size);
        assertThat(response.getCurrentPage()).isEqualTo(page);
        assertThat(response.getContent().get(0).getLiked()).isFalse();
    }

    @Test
    @DisplayName("찜한 상품 목록을 조회할 수 있다.")
    void givenLikedIsTrue_whenSearchProduct_thenProductsWithLiked() {
        //given
        Boolean liked = true;
        Integer page = 2;
        Integer size = 10;

        //when
        SearchResponse<ProductResponse> response = sut.searchProduct(liked, userId,
                page, size);

        assertThat(response.getContent().stream().allMatch(i -> i.getLiked().equals(true))).isTrue();
    }

    @Test
    @DisplayName("내가 찜하지 않은 상품 목록을 조회할 수 있다.")
    void givenLikedIsFalse_whenSearchProduct_thenProductsWithIDidNotLiked() {
        //given
        Boolean liked = false;
        Integer page = 2;
        Integer size = 10;

        //when
        SearchResponse<ProductResponse> response = sut.searchProduct(liked, userId,
                page, size);

        assertThat(response.getContent().stream().noneMatch(i -> i.getLiked().equals(true))).isTrue();
    }


}