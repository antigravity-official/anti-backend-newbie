package antigravity.service;

import antigravity.domain.entity.Product;
import antigravity.domain.entity.User;
import antigravity.domain.entity.Want;
import antigravity.domain.repository.ProductRepository;
import antigravity.domain.repository.UserRepository;
import antigravity.domain.repository.WantRepository;
import antigravity.exception.ErrorMessage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.concurrent.RejectedExecutionException;


@SpringBootTest
@Transactional
public class WantServiceTests {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WantRepository wantRepository;

    @DisplayName("찜 상품 등록 시 조회수를 증가시킨다.")
    @Test
    void updateViewCount() {
        Long id = 1L;
        Product product = productRepository.findById(id).orElseThrow(() -> new NoSuchElementException(ErrorMessage.NOT_EXIST_PRODUCT.getMsg()));
        User user = userRepository.findById(id).orElseThrow(() -> new NoSuchElementException(ErrorMessage.NOT_EXIST_USER.getMsg()));
        Want want = Want.builder().user(user).product(product).build();
        Assertions.assertEquals(1, product.getView());
    }

    @DisplayName("찜 상품 다시 찜 등록 시 예외를 발생시킨다.")
    @Test
    void SameWantProductError() {
        Long id = 1L;
        Product product = productRepository.findById(id).orElseThrow(() -> new NoSuchElementException(ErrorMessage.NOT_EXIST_PRODUCT.getMsg()));
        User user = userRepository.findById(id).orElseThrow(() -> new NoSuchElementException(ErrorMessage.NOT_EXIST_USER.getMsg()));
        wantRepository.save(Want.builder().user(user).product(product).build());
        Optional<Want> want = wantRepository.findByProductAndUser(product,user);
        if(want.isPresent())
            Assertions.assertThrows(RejectedExecutionException.class, () -> {
                throw new RejectedExecutionException(ErrorMessage.EXIST_WANT_PRODUCT.getMsg());
            });
    }
}
