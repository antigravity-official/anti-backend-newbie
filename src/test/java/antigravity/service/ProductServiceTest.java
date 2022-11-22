package antigravity.service;

import antigravity.domain.LikedProductTestBuilder;
import antigravity.domain.ProductTestBuilder;
import antigravity.domain.UserTestBuilder;
import antigravity.entity.LikedProduct;
import antigravity.entity.Product;
import antigravity.entity.User;
import antigravity.repository.LikedProductRpository;
import antigravity.repository.ProductRepository;
import antigravity.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
class ProductServiceTest {

    @MockBean
    private LikedProductRpository likedProductRpository;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    protected ProductRepository productRepository;

    @InjectMocks
    @Autowired
    private ProductService productService;

    @Nested
    @DisplayName("이미 찜한 상품이 있는지 조회하는 테스트 코드")
    class IsAlreadyLikedProduct {
        @Test
        void success() {
            // given
            List<LikedProduct> likedProducts = new ArrayList<>();
            LikedProduct likedProduct0 = LikedProductTestBuilder.createLikedProduct0();
            likedProducts.add(likedProduct0);
            when(likedProductRpository.findByUserIdAndProductId(anyLong(), anyLong())).thenReturn(likedProducts);

            // when
            boolean alreadyLikedProduct = productService.isAlreadyLikedProduct(123L, 123L);

            // then
            Assertions.assertThat(alreadyLikedProduct).isTrue();
        }
        @Test
        void fail() {
            // given
            when(likedProductRpository.findByUserIdAndProductId(anyLong(), anyLong())).thenReturn(null);

            // when
            boolean alreadyLikedProduct = productService.isAlreadyLikedProduct(123L, 123L);

            // then
            Assertions.assertThat(alreadyLikedProduct).isFalse();
        }
    }



    @Nested
    @DisplayName("상품 등록 테스트")
    class RegisterLikeProduct {
        @Test
        void success() {
            // given
            doNothing().when(likedProductRpository).save(any());
            User user = UserTestBuilder.createUser0();
            when(userRepository.findById(1L)).thenReturn(user);

            Product product = ProductTestBuilder.createLikedProduct0();
            when(productRepository.findById(1L)).thenReturn(product);

            // when
            productService.registerLikeProduct(1L, 1L);
        }

        @Test
        void failUserNull() {
            // given
            doNothing().when(likedProductRpository).save(any());
            when(userRepository.findById(1L)).thenReturn(null);

            Product product = ProductTestBuilder.createLikedProduct0();
            when(productRepository.findById(1L)).thenReturn(product);

            // when then
            Assertions.assertThatThrownBy(() -> {
                productService.registerLikeProduct(1L, 1L);
            }).isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        void failProductNull() {
            // given
            doNothing().when(likedProductRpository).save(any());
            User user = UserTestBuilder.createUser0();
            when(userRepository.findById(1L)).thenReturn(user);

            when(productRepository.findById(1L)).thenReturn(null);

            // when then
            Assertions.assertThatThrownBy(() -> {
                productService.registerLikeProduct(1L, 1L);
            }).isInstanceOf(IllegalArgumentException.class);
        }
    }
}