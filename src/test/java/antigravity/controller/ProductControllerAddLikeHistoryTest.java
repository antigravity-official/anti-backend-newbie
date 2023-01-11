package antigravity.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import antigravity.DataBaseCleanUp;
import antigravity.entity.Member;
import antigravity.entity.Product;
import antigravity.repository.LikeHistoryRepository;
import antigravity.repository.MemberRepository;
import antigravity.repository.ProductRepository;
import antigravity.repository.ProductViewCountRepository;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

/**
 * ProductController 에 있는 addLikeHistory 테스트
 */

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ProductControllerAddLikeHistoryTest {
    Member alreadySaveMember;
    Product alreadySaveProduct;
    @Autowired
    MockMvc mockMvc;
    @Autowired
    LikeHistoryRepository likeHistoryRepository;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    ProductViewCountRepository productViewCountRepository;
    @Autowired
    DataBaseCleanUp dataBaseCleanUp;

    static Stream<Arguments> notFoundErrorTest() {
        return Stream.of(
                Arguments.of(0, 1, "회원")
                , Arguments.of(1, 0, "물건")
        );
    }

    @BeforeEach
    void setUp() {
        dataBaseCleanUp.cleanUp();
        Member member = Member.builder().email("test").name("test").build();
        alreadySaveMember = memberRepository.save(member);
        Product product = Product.builder().sku("test").price(BigDecimal.ONE).name("testprodcut").quantity(10).build();
        alreadySaveProduct = productRepository.save(product);
    }

    @Test
    void successPostTest() throws Exception {
        MockHttpServletResponse response = mockMvc.perform(
                post("/products/liked/{productId}", alreadySaveProduct.getId())
                        .header("X-USER-ID", alreadySaveMember.getId())
        ).andReturn().getResponse();
        Assertions.assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
        Assertions.assertThat(1).isEqualTo(likeHistoryRepository.findAll().size());
        Assertions.assertThat(1L).isEqualTo(productViewCountRepository.findAll().get(0).getCount());
        Assertions.assertThat(alreadySaveProduct.getId())
                .isEqualTo(productViewCountRepository.findAll().get(0).getId());
    }

    @ParameterizedTest
    @MethodSource("notFoundErrorTest")
    void noFoundPostTest(long productId, long memberId, String message) throws Exception {
        MockHttpServletResponse response = mockMvc.perform(
                post("/products/liked/{productId}", productRepository.findAll().get(0).getId() + productId)
                        .header("X-USER-ID", memberRepository.findAll().get(0).getId() + memberId)
        ).andReturn().getResponse();
        Assertions.assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        Assertions.assertThat(response.getContentAsString(StandardCharsets.UTF_8)).contains(message);
    }

    @Test
    void alreadyPostTest() throws Exception {
        mockMvc.perform(
                post("/products/liked/{productId}", productRepository.findAll().get(0).getId())
                        .header("X-USER-ID", memberRepository.findAll().get(0).getId())
        ).andReturn().getResponse();
        MockHttpServletResponse response = mockMvc.perform(
                post("/products/liked/{productId}", productRepository.findAll().get(0).getId())
                        .header("X-USER-ID", memberRepository.findAll().get(0).getId())
        ).andReturn().getResponse();

        Assertions.assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        Assertions.assertThat(response.getContentAsString(StandardCharsets.UTF_8)).contains("이미");
    }
}