package antigravity.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import antigravity.application.ProductService;
import antigravity.domain.product.LikeStatus;
import antigravity.web.response.PagingInfo;
import antigravity.web.response.ProductListResponse;
import antigravity.web.response.ProductResponse;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;


@DisplayName("상품목록 조회 통합 테스트")
public class ProductSearchIntegrationTest extends IntegrationTest{

    @Autowired
    private ProductService productService;


    @Test
    @DisplayName("찜 여부 속성이 NONE 일 경우 전체 상품목록을 반환한다.")
    void search_all_product() {
        long memberId = 2L;
        PageRequest pageRequest = PageRequest.of(0, 10);
        LikeStatus status = LikeStatus.NONE;

        ProductListResponse productListResponse = productService.searchProducts(memberId,
            status, pageRequest);

        List<ProductResponse> products = productListResponse.getProducts();

        assertThat(products.size()).isEqualTo(10);
        assertThat(products.stream().anyMatch(ProductResponse::getLiked)).isTrue();
    }

    @Test
    @DisplayName("찜 여부 속성이 FALSE 일 경우 찜하지 않는 상품목록을 반환한다.")
    void search_not_liked_product() {
        long memberId = 2L;
        PageRequest pageRequest = PageRequest.of(0, 10);
        LikeStatus status = LikeStatus.FALSE;

        ProductListResponse productListResponse = productService.searchProducts(memberId, status,
            pageRequest);

        List<ProductResponse> products = productListResponse.getProducts();

        assertThat(products.size()).isEqualTo(10);
        assertThat(products.stream().allMatch(ProductResponse::getLiked)).isFalse();
    }

    @Test
    @DisplayName("찜 여부 속성이 TRUE 일 경우 찜한 상품목록을 반환한다.")
    void search_liked_product() {
        long memberId = 2L;
        PageRequest pageRequest = PageRequest.of(0, 10);
        LikeStatus status = LikeStatus.TRUE;

        ProductListResponse productListResponse = productService.searchProducts(memberId, status,
            pageRequest);

        List<ProductResponse> products = productListResponse.getProducts();
        PagingInfo pagingInfo = productListResponse.getPagingInfo();

        assertThat(pagingInfo.isLast()).isTrue();
        assertThat(products.size()).isEqualTo(3);
        assertThat(products.stream().allMatch(ProductResponse::getLiked)).isTrue();
    }

    @Test
    @DisplayName("찜 정보가 없는 유저가 TRUE 속성으로 요청할 경우 빈 응답을 반환한다.")
    void search_with_zero_like() {
        long memberId = 3L;
        PageRequest pageRequest = PageRequest.of(0, 10);
        LikeStatus status = LikeStatus.TRUE;

        ProductListResponse productListResponse = productService.searchProducts(memberId, status,
            pageRequest);

        List<ProductResponse> products = productListResponse.getProducts();
        PagingInfo pagingInfo = productListResponse.getPagingInfo();

        assertThat(products.size()).isEqualTo(0);
        assertThat(pagingInfo.isLast()).isTrue();
    }

}
