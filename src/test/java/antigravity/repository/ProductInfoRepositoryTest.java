package antigravity.repository;

import antigravity.entity.Product;
import antigravity.entity.ProductInfo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@Transactional
class ProductInfoRepositoryTest {
    @Autowired
    private ProductInfoRepository productInfoRepository;

    @DisplayName("[Repository] findById")
    @Test
    public void findByIdTest() {
        Long id = 1L;
        ProductInfo productInfo = productInfoRepository.findById(id).get();
        Assertions.assertNotNull(productInfo);
    }
}