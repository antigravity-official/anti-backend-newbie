package antigravity.product.repository;

import antigravity.product.domain.entity.DipProduct;
import antigravity.product.domain.repository.DipProductRepository;
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
public class DipRepositoryTests {
    @Autowired
    DipProductRepository dipProductRepository;
    DipProduct dipProduct;
    @BeforeEach
    void settings() {
        dipProduct = DipProduct.builder()
                .productId(1L)
                .userId(1)
                .build();
    }
    @Test
    @DisplayName("찜 상품 저장 후 찾기")
    void saveAndFind() {
        //when
        Long id = dipProductRepository.save(dipProduct);
        DipProduct findDipProduct = dipProductRepository.findById(id).get();

        //then
        assertEquals(id, findDipProduct.getProductId());
    }

    @Test
    @DisplayName("찜 상품 개수 찾기 by 유저아이디")
    void countOfDipProductByUserId() {
        //when
        for (int i = 0; i < 10; i++) {
            dipProductRepository.save(dipProduct);
        }
        int cnt = dipProductRepository.countByUserId(1);

        //then
        assertEquals(10, cnt);
    }

    @Test
    @DisplayName("유저아이디와 상품아이디로 존재 여부 확인")
    void existOfDipProductByUserIdAndProductId() {
        //when
        for (int i = 0; i < 10; i++) {
            dipProductRepository.save(dipProduct);
        }
        int cnt = dipProductRepository.existsDipProductByUserIdAndProductId(1,1L);

        //then
        assertEquals(10,cnt);
    }

    @Test
    @DisplayName("유저아이디로 모든 찜상품 찾기")
    void findAllDipProductByUserId() {
        //when
        for (int i = 0; i < 100; i++) {
            dipProductRepository.save(dipProduct);
        }

        Page<DipProduct> dipProducts = dipProductRepository.findAllByUserId(1, PageRequest.of(0, 10));

        //then
        assertEquals(10, dipProducts.getSize());
        assertEquals(0,dipProducts.getNumber());
    }
}
