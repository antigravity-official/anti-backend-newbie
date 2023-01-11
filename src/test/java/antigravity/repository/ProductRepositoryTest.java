package antigravity.repository;

import antigravity.config.PageParam;
import antigravity.entity.Product;
import antigravity.payload.ProductResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.util.List;


@SpringBootTest
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    void findById() {
        Long productid = 1L;
        Product result = productRepository.findById(productid);
        Assert.notNull(result);
    }

    @Test
    void productLiked() {
        Long userId = 2L;
        Long productId = 1L;
        String result = productRepository.productLiked(userId, productId);
        Assertions.assertEquals("201", result);
    }

    @Test
    void favoriteProductFalseList() {
        PageParam param = new PageParam(1, 20);
        Long userId = 3L;
        boolean liked = false;
        List<ProductResponse> productRepositoryList = productRepository.favoriteProductList(param,userId, liked);
        System.out.println(productRepositoryList.size());
        Assertions.assertEquals(18, productRepositoryList.size());

    }

    @Test
    void favoriteProductTrueList(){
        PageParam param = new PageParam(1, 5);
        Long userId = 3L;
        boolean liked = true;
        List<ProductResponse> productResponses = productRepository.favoriteProductList(param,userId,liked);
        Assertions.assertEquals(2,productResponses.size());
    }

    @Test
    void findAllProductList() {
        PageParam param = new PageParam(1, 20);
        Long userId = 3L;
        List<ProductResponse> productResponses = productRepository.findAllProductList(param,userId);
        Assertions.assertEquals(19,productResponses.size());
    }
}