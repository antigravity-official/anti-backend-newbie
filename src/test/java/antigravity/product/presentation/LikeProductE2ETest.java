package antigravity.product.presentation;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import antigravity.product.domain.Product;
import antigravity.product.repository.ProductJpaRepository;
import antigravity.product.repository.ProductLikeJpaRepository;
import antigravity.product.repository.ProductViewJpaRepository;
import antigravity.user.domain.User;
import antigravity.user.repository.UserJpaRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@AutoConfigureMockMvc
@Transactional
@SpringBootTest
public class LikeProductE2ETest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserJpaRepository userJpaRepository;

    @Autowired
    private ProductJpaRepository productJpaRepository;

    @Autowired
    private ProductLikeJpaRepository productLikeJpaRepository;

    @Autowired
    private ProductViewJpaRepository productViewJpaRepository;

    @Test
    @DisplayName("인증 정보와 상품 ID로 해당 상품을 찜할 수 있다.")
    void givenUserIdAndProductId_whenLikeProduct_thenLiked() throws Exception {
        //given
        User user = userJpaRepository.findAll().stream().filter(u -> !u.isDeleted())
                .findAny().get();
        Long userId = user.getId();
        Product product = productJpaRepository.findAll().stream().filter(p -> !p.isDeleted())
                .findAny().get();
        Long productId = product.getId();
        //expected
        mockMvc.perform(
                        post("/products/liked/{productId}", productId)
                                .header("X-USER-ID", userId)
                ).andExpect(status().isCreated())
                .andDo(print());

        assertThat(productViewJpaRepository.findAll().stream()
                .anyMatch(pv -> pv.getProductId().equals(productId))).isTrue();
        assertThat(productLikeJpaRepository.existsByProductIdAndUserId(productId, userId)).isTrue();

    }
}
