package antigravity.repository;

import antigravity.entity.Heart;
import antigravity.entity.Product;
import antigravity.entity.ViewCount;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
@Transactional
public class ProductRepositoryTests {

    @Autowired
    private ProductRepository repository;

    @Test
    public void findByIdTest() {
        Long id = 1L;
        Product product = repository.findById(id);
        Assertions.assertNotNull(product);
    }

    @Test
    public void isAlreadyLikedTest() {
        Long memberId = 4L;
        Long productId = 1L;
        List<Heart> heart = repository.isAlreadyLiked(memberId, productId);
        Assertions.assertNotNull(heart);
    }

    @Test
    public void deleteLikedProductTest() {
        Long memberId = 4L;
        Long productId = 1L;
        Assertions.assertEquals(1, repository.deleteLikedProduct(memberId, productId));
    }

    @Test
    public void checkViewCountTest() {
        Long productId = 1L;
        ViewCount viewCount = repository.checkViewCount(productId);
        Assertions.assertNotNull(viewCount);
    }

    @Test
    public void updateViewCountTest() {
        Long productId = 1L;
        Assertions.assertEquals(1, repository.updateViewCount(productId));
    }

    @Test
    public void addViewCountTest() {
        Long productId = 2L;
        Assertions.assertEquals(1, repository.addViewCount(productId));
    }

    @Test
    public void likeProductTest() {
        Long memberId = 3L;
        Long productId = 1L;
        Assertions.assertEquals(1, repository.likeProduct(memberId,productId));
    }

    @Test
    public void getAllProductTest() {

    }
}
