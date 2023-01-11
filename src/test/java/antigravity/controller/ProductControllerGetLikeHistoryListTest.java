package antigravity.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import antigravity.DataBaseCleanUp;
import antigravity.entity.LikeHistory;
import antigravity.entity.Member;
import antigravity.entity.Product;
import antigravity.payload.ProductResponse;
import antigravity.repository.LikeHistoryRepository;
import antigravity.repository.MemberRepository;
import antigravity.repository.ProductRepository;
import antigravity.repository.ProductViewCountRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.List;
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
import org.springframework.util.LinkedMultiValueMap;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ProductControllerGetLikeHistoryListTest {
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

    @Autowired
    ObjectMapper objectMapper;

    static Stream<Arguments> successNotNullArguments() {
        return Stream.of(
                Arguments.of(true, 2)
                , Arguments.of(false, 1)
        );
    }

    static Stream<Arguments> errorArguments() {
        return Stream.of(
                Arguments.of("0", "qwe", 0, "파라미터")
                , Arguments.of("qweqwe", "0", 0, "파라미터")
                , Arguments.of("0", "qwe", 1, "회원")
        );
    }

    @BeforeEach
    public void setUp() {
        dataBaseCleanUp.cleanUp();
        Member member = Member.builder().email("test").name("test").build();
        alreadySaveMember = memberRepository.save(member);
        Product product = Product.builder().sku("test").price(BigDecimal.ONE).name("testprodcut").quantity(10).build();
        alreadySaveProduct = productRepository.save(product);
        likeHistoryRepository.save(LikeHistory.builder().member(alreadySaveMember).product(alreadySaveProduct).build());

        likeHistoryRepository.save(
                LikeHistory.builder()
                        .member(memberRepository.save(Member.builder().email("test").name("test").build()))
                        .product(alreadySaveProduct).build()
        );
        likeHistoryRepository.save(
                LikeHistory.builder()
                        .member(alreadySaveMember)
                        .product(productRepository.save(
                                Product.builder().sku("test").price(BigDecimal.ONE).name("testprodcut123").quantity(10)
                                        .build())).build()
        );
        productRepository.save(
                Product.builder().sku("test").price(BigDecimal.TEN).name("testprodcut1").quantity(10).build());
    }

    @Test
    void successNullTest() throws Exception {
        LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("liked", null);
        requestParams.add("page", "0");
        requestParams.add("size", "3");

        MockHttpServletResponse response = mockMvc.perform(
                get("/products/liked?")
                        .params(requestParams)
                        .header("X-USER-ID", alreadySaveMember.getId())
        ).andReturn().getResponse();
        List<ProductResponse> expect = objectMapper.readValue(response.getContentAsString(StandardCharsets.UTF_8),
                new TypeReference<List<ProductResponse>>() {
                });

        Assertions.assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        Assertions.assertThat(expect.get(0).getLiked()).isNotEqualTo(null);
        Assertions.assertThat(expect.size()).isEqualTo(3);
    }

    @ParameterizedTest
    @MethodSource("successNotNullArguments")
    void successNotNullTest(boolean liked, int size) throws Exception {
        LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("liked", "" + liked);
        requestParams.add("page", "0");
        requestParams.add("size", "3");

        MockHttpServletResponse response = mockMvc.perform(
                get("/products/liked?")
                        .params(requestParams)
                        .header("X-USER-ID", alreadySaveMember.getId())
        ).andReturn().getResponse();
        List<ProductResponse> expect = objectMapper.readValue(response.getContentAsString(StandardCharsets.UTF_8),
                new TypeReference<List<ProductResponse>>() {
                });
        Assertions.assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        Assertions.assertThat(expect.get(0).getLiked()).isEqualTo(null);
        Assertions.assertThat(expect.size()).isEqualTo(size);
    }

    @ParameterizedTest
    @MethodSource("errorArguments")
    void successNotNullTest(String size, String page, int id, String message) throws Exception {
        LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("liked", "" + true);
        requestParams.add("page", "" + size);
        requestParams.add("size", "" + page);

        MockHttpServletResponse response = mockMvc.perform(
                get("/products/liked?")
                        .params(requestParams)
                        .header("X-USER-ID", alreadySaveMember.getId() + id)
        ).andReturn().getResponse();

        Assertions.assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        Assertions.assertThat(response.getContentAsString(StandardCharsets.UTF_8)).contains(message);

    }
}