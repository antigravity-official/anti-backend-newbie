package antigravity.product.presentation;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import antigravity.product.domain.Product;
import antigravity.product.domain.ProductLike;
import antigravity.product.domain.ProductView;
import antigravity.product.repository.ProductJpaRepository;
import antigravity.product.repository.ProductLikeJpaRepository;
import antigravity.product.repository.ProductViewJpaRepository;
import antigravity.user.domain.User;
import antigravity.user.repository.UserJpaRepository;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.BeforeEach;
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
public class SearchProductE2ETest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserJpaRepository userJpaRepository;

    @Autowired
    private ProductJpaRepository productJpaRepository;

    @Autowired
    private ProductViewJpaRepository productViewJpaRepository;

    @Autowired
    private ProductLikeJpaRepository productLikeJpaRepository;

    private Long userId;

    @BeforeEach
    void beforeEach() {
        User user = userJpaRepository.findAll().stream().filter(u -> !u.isDeleted())
                .findAny().get();
        userId = user.getId();
        List<Product> products = productJpaRepository.findAll().stream().filter(p -> !p.isDeleted())
                .collect(Collectors.toList());
        for (Product product : products) {
            Long productId = product.getId();
            if (productId % 2 == 0) {
                ProductView productView = ProductView.builder()
                        .productId(productId)
                        .build();
                productViewJpaRepository.save(productView);
                ProductLike productLike = ProductLike.builder()
                        .productId(productId)
                        .userId(userId)
                        .build();
                productLikeJpaRepository.save(productLike);
            }
            if (productId % 4 == 0) {
                ProductView productView = ProductView.builder()
                        .productId(productId)
                        .build();
                productViewJpaRepository.save(productView);
            }
        }
    }

    @Test
    @DisplayName("모든 상품 목록을 조회할 수 있다.")
    void whenSearchProductWithUser_thenAllProducts() throws Exception {
        //expected
        mockMvc.perform(
                        get("/products/liked")
                                .header("X-USER-ID", userId)
                ).andExpect(status().isOk())
                .andExpect(jsonPath("$.totalElements").value(20))
                .andExpect(jsonPath("$.currentPage").value(1))
                .andDo(print())
        ;
    }

    @Test
    @DisplayName("찜한 상품 목록을 조회할 수 있다.")
    void givenLikedIsTrue_whenSearchProductWithUser_thenProductsWithLiked() throws Exception {
        //given
        Boolean liked = true;

        //expected
        mockMvc.perform(
                        get("/products/liked")
                                .header("X-USER-ID", userId)
                                .param("liked", String.valueOf(liked))
                                .param("size", String.valueOf(5))
                ).andExpect(status().isOk())
                .andExpect(jsonPath("$.totalElements").value(10))
                .andExpect(jsonPath("$.currentPage").value(1))
                .andExpect(jsonPath("$.content[*].liked",
                        is(Arrays.asList(true, true, true, true, true))))
                .andDo(print());

    }

    @Test
    @DisplayName("찜하지 않은 상품 목록을 조회할 수 있다.")
    void givenLikedIsFalse_whenSearchProductWithUser_thenProductsWithDidNotLiked()
            throws Exception {
        //given
        Boolean liked = false;

        //expected
        mockMvc.perform(
                        get("/products/liked")
                                .header("X-USER-ID", userId)
                                .param("liked", String.valueOf(liked))
                                .param("size", String.valueOf(5))
                ).andExpect(status().isOk())
                .andExpect(jsonPath("$.totalElements").value(10))
                .andExpect(jsonPath("$.currentPage").value(1))
                .andExpect(jsonPath("$.content[*].liked",
                        is(Arrays.asList(false, false, false, false, false))))
                .andDo(print());

    }

}
