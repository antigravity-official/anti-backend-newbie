package antigravity.product.service;

import antigravity.global.redis.RedisDao;
import antigravity.product.domain.entity.Product;
import antigravity.product.domain.repository.ProductRepository;
import antigravity.product.service.impl.ViewServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class ViewServiceTests {
    @Autowired
    ViewServiceImpl viewService;
    @Autowired
    RedisDao redisDao;
    @Autowired
    private ProductRepository productRepository;
    Product product;
    Long productId;
    @BeforeEach
    void settings() {
        product = Product.builder()
                .createdAt(LocalDateTime.now())
                .viewed(1L)
                .name("No1. 더핏세트")
                .quantity(10)
                .price(BigDecimal.valueOf(42800))
                .sku("G2000000019")
                .build();
        productId = productRepository.save(product);
    }

    @Test
    @DisplayName("레디스 DB내부의 조회수가 증가한다.")
    void addViewCntToRedis() {
        //when
        long cnt = 0;
        for (int i = 0; i < 100; i++) {
            cnt = viewService.addViewCntToRedis(productId);
        }
        //then
        assertEquals(cnt, Long.parseLong(redisDao.getData("productViewCnt::" + productId)));
    }

    @Test
    @DisplayName("레디스에서 특정 시간마다 조회수를 삭제할 수 있다.")
    void deleteViewCnt() {
        //when
        viewService.addViewCntToRedis(productId);
        viewService.deleteViewCntCacheFromRedis();

        //then
        assertNull(redisDao.getData("productViewCnt::" + productId));
    }
}
