package antigravity.service;

import antigravity.entity.Product;
import antigravity.global.exception.BusinessException;
import antigravity.global.exception.ErrorCode;
import antigravity.global.exception.NotFoundException;
import antigravity.repository.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class ProductServiceTest {

    @Autowired
    private ProductService productService;
    @Autowired
    private ProductRepository productRepository;

    private final Long testerId = 99998L;
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
    void addWish() {
        Long newProduct = 99990L;
        productService.addWish(testerId, newProduct);

        Product product = productRepository.findById(newProduct).orElseThrow(() -> new NotFoundException(ErrorCode.PRODUCT_NOT_FOUND));
        Assertions.assertEquals(1, product.getView());

    }
}