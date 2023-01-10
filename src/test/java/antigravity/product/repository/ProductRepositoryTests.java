package antigravity.product.repository;

import antigravity.product.domain.entity.Product;
import antigravity.product.domain.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class ProductRepositoryTests {

    @Autowired
    private ProductRepository productRepository;
    Product product;
    @BeforeEach
    void setting() {
        product = Product.builder()
                .createdAt(LocalDateTime.now())
                .viewed(1L)
                .name("No1. 더핏세트")
                .quantity(10)
                .price(BigDecimal.valueOf(42800))
                .sku("G2000000019")
                .build();
    }
    @Test
    public void saveAndFind() throws Exception {
        //when
        Long id = productRepository.save(product);
        Product findProduct = productRepository.findById(id).get();

        //then
        assertEquals(id, findProduct.getId());
    }
    @Test
    public void findViewCnt() throws Exception {
        //when
        Long id = productRepository.save(product);
        Long viewCnt = productRepository.findProductViewCnt(id);

        //then
        assertEquals(product.getViewed(), viewCnt);
    }

    @Test
    public void updateViewCnt() throws Exception {
        //when
        Long id = productRepository.save(product);
        productRepository.updateViewCntFromRedis(id, 100L);

        Product findProduct = productRepository.findById(id).get();

        //then
        assertEquals(findProduct.getViewed(), 100L);
    }


}
