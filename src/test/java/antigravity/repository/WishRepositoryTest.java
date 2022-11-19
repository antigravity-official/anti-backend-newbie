package antigravity.repository;

import antigravity.entity.Wish;
import antigravity.global.exception.BusinessException;
import antigravity.global.exception.ErrorCode;
import antigravity.global.exception.NotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@SpringBootTest
@Transactional
class WishRepositoryTest {

    private final Long testerId = 99998L;
    private final Long productTestId = 99999L;

    @Autowired
    private WishRepository wishRepository;

    @DisplayName("사용자 ID와 상품 ID로 찜한 것을 찾을 수 있다.")
    @Test
    void findById() {
        Wish wish = wishRepository.findById(testerId, productTestId).orElseThrow(() -> new NoSuchElementException("찜 하지 않았습니다."));

        Assertions.assertEquals(productTestId, wish.getProductId());
        Assertions.assertEquals(testerId, wish.getUserId());
    }

    @DisplayName("사용자가 맘에 드는 상품을 찜할 수 있다.")
    @Test
    void save() {
        Long newProductId = 1L;
        wishRepository.save(testerId, newProductId);
        Wish wish = wishRepository.findById(testerId, newProductId).orElseThrow(() -> new NoSuchElementException("찜 하지 않았습니다."));
        Assertions.assertEquals(newProductId, wish.getProductId());
        Assertions.assertEquals(testerId, wish.getUserId());
    }


}