package antigravity.service;

import antigravity.domain.LikedProductTestBuilder;
import antigravity.domain.ProductTestBuilder;
import antigravity.domain.UserTestBuilder;
import antigravity.entity.LikedProduct;
import antigravity.entity.Product;
import antigravity.entity.Customer;
import antigravity.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Nested;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
class CustomerServiceTest {
    @MockBean
    private UserRepository userRepository;

    @Autowired
    @InjectMocks
    private UserService userService;

    @Nested
    class IsLikeProduct {
        @Test
        void sucess() {
            Customer customer0 = UserTestBuilder.createUser0();
            LikedProduct likedProduct01 = LikedProductTestBuilder.createLikedProduct0();
            customer0.addLikeProducts(likedProduct01);

            Product product = ProductTestBuilder.createLikedProduct0();
            when(userRepository.findById(customer0.getId())).thenReturn(customer0);

            boolean likeProduct = userService.isLikeProduct(customer0.getId(), product.getId());
            Assertions.assertThat(likeProduct).isTrue();
        }
        @Test
        void fail() {
            Customer customer0 = UserTestBuilder.createUser0();

            Product product = ProductTestBuilder.createLikedProduct0();
            when(userRepository.findById(customer0.getId())).thenReturn(customer0);

            boolean likeProduct = userService.isLikeProduct(customer0.getId(), product.getId());
            Assertions.assertThat(likeProduct).isFalse();
        }
    }

}