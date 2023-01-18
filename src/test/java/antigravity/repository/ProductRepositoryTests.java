package antigravity.repository;

import antigravity.entity.Product;
import antigravity.entity.Member;
import antigravity.entity.Wanted;
import org.junit.jupiter.api.Assertions;
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
        Product product = productRepository.findById(id);
        Assertions.assertNotNull(product);
    }

    @Test
    public void findByUserTest() {
        String email = "user2@antigravity.kr";
        Member user = productRepository.findUserByEmail(email);
        Assertions.assertNotNull(user);
    }

    @Test
    public void ImportWanted() {
        Wanted wanted = null;
        String sku = "1111111";
        String user_id = "1112111111";
        System.out.println(sku);
        productRepository.ImportWanted(sku, user_id);
        Wanted wanted1 = productRepository.findWaById(1L);
        Assertions.assertNotNull(wanted1);
    }

}
