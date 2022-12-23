package antigravity.repository;

import antigravity.entity.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
@Transactional
public class ProductRepositoryTests {

    @Autowired
    private ProductRepository productRepository;

    @Test
    public void findByIdTest() {
        Long id = 1L;
        Product product = productRepository.findById(id).orElseThrow();
        assertNotNull(product);
        assertNotNull(product.getCreatedAt());

        assertNull(product.getDeletedAt());
    }

}
