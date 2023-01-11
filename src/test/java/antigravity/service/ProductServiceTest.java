package antigravity.service;

import antigravity.config.PageParam;
import antigravity.payload.ProductResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @Test
    void createFavorite() {
        Long userId = 3L;
        Long productId = 3L;
        String value = productService.createFavorite(userId,productId);
        Assertions.assertEquals("201", value);
    }

    @Test
    void createFavoriteFalse(){
        Long userId = 1L;
        Long productId = 3L;
        String value = productService.createFavorite(userId, productId);

        Assertions.assertEquals("401", value);
    }

    @Test
    void favoriteProductList() {
        PageParam param = new PageParam(1, 10);
        Long id = 3L;
        List<ProductResponse> pr = productService.favoriteProductList(param,id, true);
        Assertions.assertEquals(2, pr.size());
    }

    @Test
    void favoriteProductFalse(){
        PageParam param = new PageParam(1, 10);
        Long id = 3L;
        List<ProductResponse> pr = productService.favoriteProductList(param,id, false);
        Assertions.assertEquals(10, pr.size());
    }

    @Test
    void fallProductUser(){
        PageParam param = new PageParam(1, 10);
        Long id = 1L;
        List<ProductResponse> pr = productService.favoriteProductList(param,id, false);
        Assertions.assertEquals(null, pr);
    }


    @Test
    void findAllProductList() {
        PageParam param = new PageParam(1, 10);
        Long id = 3L;
        List<ProductResponse> pr = productService.findAllProductList(param,id);
        Assertions.assertEquals(true, pr.get(0).getLikes());
    }

    @Test
    void findAllFallUser(){
        PageParam param = new PageParam(1, 10);
        Long id = 1L;
        List<ProductResponse> pr = productService.findAllProductList(param,id);
        Assertions.assertEquals(null, pr);
    }

}