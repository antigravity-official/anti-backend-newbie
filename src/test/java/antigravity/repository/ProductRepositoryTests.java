package antigravity.repository;

import antigravity.entity.Product;
import antigravity.global.exception.ErrorCode;
import antigravity.global.exception.NotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class ProductRepositoryTests {

    @Autowired
    private ProductRepository productRepository;

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

}
