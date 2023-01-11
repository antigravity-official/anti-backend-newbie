package antigravity.product.repository;

import antigravity.product.domain.entity.LikeProduct;
import antigravity.product.domain.repository.LikeProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class LikeProductRepositoryTests {
    @Autowired
    LikeProductRepository likeProductRepository;
    LikeProduct likeProduct;
    @BeforeEach
    void settings() {
        likeProduct = LikeProduct.builder()
                .productId(1L)
                .userId(1)
                .build();
    }
    @Test
    @DisplayName("찜 상품을 저장 한 뒤 상품 아이디로 찾을 수 있다.")
    void saveAndFind() {
        //when
        Long id = likeProductRepository.save(likeProduct);
        LikeProduct findLikeProduct = likeProductRepository.findById(id).get();

        //then
        assertEquals(likeProduct.getProductId(), findLikeProduct.getProductId());
    }

    @Test
    @DisplayName("유저아이디로 찜 상품 개수를 찾을 수 있다.")
    void countOfDipProductByUserId() {
        //when
        for (int i = 0; i < 10; i++) {
            likeProductRepository.save(likeProduct);
        }
        int cnt = likeProductRepository.countDipProductByUserId(1);

        //then
        assertEquals(10, cnt);
    }

    @Test
    @DisplayName("상품아이디로 찜 상품 개수를 찾을 수 있다.")
    void countOfDipProductByProductId() {
        //when
        for (int i = 0; i < 10; i++) {
            likeProductRepository.save(likeProduct);
        }
        int cnt = likeProductRepository.countDipProductByProductId(1L);

        //then
        assertEquals(10, cnt);
    }

    @Test
    @DisplayName("유저아이디와 상품아이디로 찜상품의 존재 여부를 확인할 수 있다.")
    void existOfDipProductByUserIdAndProductId() {
        //when
        for (int i = 0; i < 10; i++) {
            likeProductRepository.save(likeProduct);
        }
        int cnt = likeProductRepository.existsDipProductByUserIdAndProductId(1,1L);

        //then
        assertEquals(10,cnt);
    }

    @Test
    @DisplayName("유저아이디로 모든 찜상품을 찾을 수 있다.")
    void findAllDipProductByUserId() {
        //when
        for (int i = 0; i < 100; i++) {
            likeProductRepository.save(likeProduct);
        }

        Page<LikeProduct> dipProducts = likeProductRepository.findAllByUserId(1, PageRequest.of(0, 10));

        //then
        assertEquals(10, dipProducts.getSize());
        assertEquals(0,dipProducts.getNumber());
    }
}
