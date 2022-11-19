package antigravity.repository;

import antigravity.entity.Product;
import antigravity.global.common.ErrorCode;
import antigravity.global.exception.NotFoundException;
import antigravity.payload.ProductRequest;
import antigravity.payload.ProductResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@SpringBootTest
@Transactional
public class ProductRepositoryTests {

    @Autowired
    private ProductRepository productRepository;

    private final Long TESTER = 10000L;

    @Test
    public void findByIdTest() {
        Long id = 1L;
        Product product = productRepository.findById(id).orElseThrow(() -> new NotFoundException(ErrorCode.PRODUCT_NOT_FOUND));
        Assertions.assertNotNull(product);
        Assertions.assertEquals(1L, product.getId());
        Assertions.assertEquals(0, product.getView());
    }

    @DisplayName("찾는 상품이 없으면 null을 리턴한다.")
    @Test
    public void findByIdFail() {
        Long id = -1L;
        Assertions.assertEquals(Optional.empty(), productRepository.findById(id));
    }

    @DisplayName("View의 카운트를 증가시킨다.")
    @Test
    void updateViewCount() {
        Long id = 1L;
        Product product = productRepository.findById(id).orElseThrow(() -> new NotFoundException(ErrorCode.PRODUCT_NOT_FOUND));
        productRepository.updateViewCount(product);

        Product updatedProduct = productRepository.findById(id).orElseThrow(() -> new NotFoundException(ErrorCode.PRODUCT_NOT_FOUND));
        Assertions.assertEquals(1, updatedProduct.getView());
    }

    @DisplayName("모든 상품 목록을 조회한다. (삭제된 상품 제외 총 23개 존재)")
    @ParameterizedTest
    @MethodSource("productRequestParam")
    void getProductList(Boolean liked, int page, int size, int resultSize) {
        ProductRequest request = new ProductRequest(liked, page, size);
        List<Product> results = productRepository.getProductList(request);
        Assertions.assertEquals(resultSize, results.size());
        int pagedFirstId = (page * 10) + 1; // 1 ~ 21번 더미 데이터는 liked = 0이고 삭제되지 않은 상품이다.
        Assertions.assertEquals(pagedFirstId, results.get(0).getId());
    }

    private static Stream<Arguments> productRequestParam() {
        return Stream.of(
                Arguments.of(null, 0, 10, 10),
                Arguments.of(null, 1, 10, 10),
                Arguments.of(null, 2, 10, 3)
        );
    }

    @DisplayName("찜한 상품목록을 조회한다. (총 2개 존재)")
    @Test
    void getWishList() {
        ProductRequest request = new ProductRequest(true, 0, 10);
        List<Product> wishList = productRepository.getWishList(TESTER, request);
        Assertions.assertEquals(2, wishList.size());
    }

    @DisplayName("찜하지 않는 상품 목록을 조회한다. (총 21개 존재)")
    @ParameterizedTest
    @MethodSource("notWishRequestParam")
    void getNotWishProductList(Boolean liked, int page, int size, int resultSize) {
        ProductRequest request = new ProductRequest(liked, page, size);
        List<Product> productList = productRepository.getNotWishProductList(TESTER, request);
        int pagedFirstId = (page * 10) + 1; // 1 ~ 21번 더미 데이터는 liked = 0이고 삭제되지 않은 상품이다.
        Assertions.assertEquals(pagedFirstId, productList.get(0).getId());
        Assertions.assertEquals(resultSize, productList.size());
    }

    private static Stream<Arguments> notWishRequestParam() {
        return Stream.of(
                Arguments.of(false, 0, 10, 10),
                Arguments.of(false, 1, 10, 10),
                Arguments.of(false, 2, 10, 1)
        );
    }

}
