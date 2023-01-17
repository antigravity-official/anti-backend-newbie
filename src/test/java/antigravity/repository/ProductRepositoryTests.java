package antigravity.repository;

import antigravity.entity.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@SpringBootTest
@Transactional
public class ProductRepositoryTests {

    @Autowired
    private ProductRepository productRepository;

    @DisplayName("[Repository] findById")
    @Test
    public void findByIdTest() {
        Long id = 1L;
        Product product = productRepository.findById(id);
        Assertions.assertNotNull(product);
    }

}
