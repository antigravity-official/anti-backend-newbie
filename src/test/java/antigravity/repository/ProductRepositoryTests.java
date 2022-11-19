package antigravity.repository;

import antigravity.entity.Product;
import antigravity.global.common.ErrorCode;
import antigravity.global.exception.NotFoundException;
import antigravity.payload.ProductRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    @DisplayName("View의 카운트를 증가시킨다.")
    @Test
    void updateViewCount() {
        Long id = 1L;
        Product product = productRepository.findById(id).orElseThrow(() -> new NotFoundException(ErrorCode.PRODUCT_NOT_FOUND));
        productRepository.updateViewCount(product);

        Product updatedProduct = productRepository.findById(id).orElseThrow(() -> new NotFoundException(ErrorCode.PRODUCT_NOT_FOUND));
        Assertions.assertEquals(1, updatedProduct.getView());
    }

    @DisplayName("모든 상품 목록을 조회한다. (총 23개 존재)")
    @Test
    void getProductList() {
        ProductRequest request = new ProductRequest(null, 0, 10);
        List<Product> results = productRepository.getProductList(request);
        Assertions.assertEquals(10, results.size());
    }

    @DisplayName("찜한 상품목록을 조회한다. (총 2개 존재)")
    @Test
    void getWishList() {
        ProductRequest request = new ProductRequest(null, 0, 10);
        List<Product> wishList = productRepository.getWishList(TESTER, request);
        Assertions.assertEquals(2, wishList.size());
    }

    @DisplayName("찜하지 않는 상품 목록을 조회한다. (총 21개 존재)")
    @Test
    void getNotWishProductList() {
        ProductRequest request = new ProductRequest(null, 0, 10);
        List<Product> productList = productRepository.getNotWishProductList(TESTER, request);
        Assertions.assertEquals(10, productList.size());
    }

}
