package antigravity.service;

import antigravity.entity.Product;
import antigravity.global.exception.BusinessException;
import antigravity.global.common.ErrorCode;
import antigravity.global.exception.NotFoundException;
import antigravity.payload.ProductRequest;
import antigravity.payload.ProductResponse;
import antigravity.repository.ProductRepository;
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
import java.util.stream.Stream;

@SpringBootTest
@Transactional
class ProductServiceTest {

    @Autowired
    private ProductService productService;
    @Autowired
    private ProductRepository productRepository;

    private final Long testerId = 10000L;
    private final Long productId = 99999L;

    @DisplayName("이미 찜한 상품인 경우 BusinessException 발생한다.")
    @Test
    void addWishFailWithDuplicated() {
        Assertions.assertThrows(BusinessException.class, () -> productService.addWish(testerId, productId));
    }

    @DisplayName("만약 존재하지 상품이라면 NotFoundException 발생한다.")
    @Test
    void addWishFailWithNotFound() {
        Assertions.assertThrows(NotFoundException.class, () -> productService.addWish(testerId, -1L));
    }

    @DisplayName("찜을 하면, 상품의 조회수가 1 증가한다.")
    @Test
    void addWishWithSingleThread() {
        Long newProduct = 1L;
        productService.addWish(testerId, newProduct);

        Product product = productRepository.findById(newProduct).orElseThrow(() -> new NotFoundException(ErrorCode.PRODUCT_NOT_FOUND));
        Assertions.assertEquals(1, product.getView());
    }

    @DisplayName("요청 파라미터의 liked가 null이라면 모든 상품을 조회한다.")
    @ParameterizedTest
    @MethodSource("productRequestParams")
    void getProductList(Boolean liked, int page, int size, int resultSize) {
        List<ProductResponse> productOrWishList = productService.getProductOrWishList(testerId, new ProductRequest(liked, page, size));
        int pagedFirstId = (page * 10) + 1; // 1 ~ 21번 더미 데이터는 liked = 0이고 삭제되지 않은 상품이다.
        Assertions.assertEquals(pagedFirstId, productOrWishList.get(0).getId());
        Assertions.assertEquals(resultSize, resultSize);
    }

    private static Stream<Arguments> productRequestParams() {
        return Stream.of(
                Arguments.of(null, 0, 10, 10),
                Arguments.of(null, 1, 10, 10),
                Arguments.of(null, 2, 10, 3)
        );
    }

    @DisplayName("요청 파라미터의 liked가 true라면 찜한 상품만 조회한다.")
    @Test
    void getWishList() {
        List<ProductResponse> productOrWishList = productService.getProductOrWishList(testerId, new ProductRequest(true, 0, 10));
        Assertions.assertEquals(2, productOrWishList.size());
        for (ProductResponse productResponse : productOrWishList) {
            Assertions.assertEquals(1, productResponse.getViewed());
            Assertions.assertEquals(true, productResponse.getLiked());
        }
    }


    @DisplayName("요청 파라미터의 liked가 false라면 찜하지 않은 상품만 조회하며 liked = false여야 한다.")
    @ParameterizedTest
    @MethodSource("notWishRequestParams")
    void getNotWishedProductList(Boolean liked, int page, int size, int resultSize) {
        List<ProductResponse> productOrWishList = productService.getProductOrWishList(testerId, new ProductRequest(liked, page, size));
        Assertions.assertEquals(resultSize, productOrWishList.size());
        for (ProductResponse productResponse : productOrWishList) {
            Assertions.assertEquals(false, productResponse.getLiked());
        }
    }

    private static Stream<Arguments> notWishRequestParams() {
        return Stream.of(
                Arguments.of(false, 0, 10, 10),
                Arguments.of(false, 1, 10, 10),
                Arguments.of(false, 2, 10, 1)
        );
    }
}