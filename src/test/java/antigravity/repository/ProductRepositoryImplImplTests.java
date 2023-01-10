package antigravity.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class ProductRepositoryImplImplTests {

    @Autowired
    private ProductRepositoryImpl productRepositoryImpl;

    @Test
    public void findByIdTest() {
    }

}
