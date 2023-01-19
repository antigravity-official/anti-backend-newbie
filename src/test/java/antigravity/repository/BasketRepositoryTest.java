package antigravity.repository;

import antigravity.entity.Basket;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class BasketRepositoryTest {

    @Autowired
    private BasketRepository basketRepository;

    @DisplayName("[Repository] findById")
    @Test
    public void findByIdTest() {
        Long id = 1L;
        Basket basket = basketRepository.findById(id).get();
        Assertions.assertNotNull(basket);
    }

    @DisplayName("[Repository] findById")
    @Test
    public void findAllByUserIdTest() {
        Long id = 1L;
        //Basket basket = (Basket) basketRepository.finaAllByUserId(id, Pageable.unpaged()).get();
     //   Assertions.assertNotNull(basket);
    }
}