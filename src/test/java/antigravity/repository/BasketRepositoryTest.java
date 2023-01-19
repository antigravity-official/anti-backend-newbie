package antigravity.repository;

import antigravity.entity.Basket;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
@Transactional
class BasketRepositoryTest {

    @Autowired
    private BasketRepository basketRepository;

    @DisplayName("[Repository] findAllByUserIdTest")
    @Test
    public void findAllByUserIdTest() {

        // given
        basketRepository.save(Basket.of(false, 1L, 1L));
        Long id = 1L;

        // When & Then
        List<IdMapping> list = basketRepository.findAllByUserId(id);

        Assertions.assertNotNull(list);
    }
}