package antigravity.controller;

import antigravity.entity.Product;
import antigravity.global.common.Constants;
import antigravity.global.common.ErrorCode;
import antigravity.global.exception.NotFoundException;
import antigravity.repository.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindException;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Stream;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class ProductControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    private ProductRepository productRepository;
    private final Long TESTER = 10000L;
    private final Long PRODUCT_TEST_ID = 99999L; // 찜이 이미 되어있는 상품
    private final Long NEW_PRODUCT_TEST_ID = 1L;

    @DisplayName("헤더에 유저 정보가 없는 경우")
    @Test
    void userInterceptorFailTest() throws Exception {
        mockMvc.perform(post("/products/liked/{productId}", PRODUCT_TEST_ID))
                .andExpect(status().is4xxClientError())
                .andExpect(content().string("{\"statusCode\":\"400 BAD_REQUEST\",\"data\":\"Can't find a header info\"}"));
    }

    @DisplayName("헤더에 유저 정보가 있고, 이미 찜한 500(DuplicatedEntityException) 발생")
    @Test
    void addWishFailTest() throws Exception {
        mockMvc.perform(post("/products/liked/{productId}", PRODUCT_TEST_ID)
                        .header(Constants.USER_ID_HEADER, TESTER))
                .andExpect(status().is4xxClientError())
                .andExpect(content().string("{\"statusCode\":\"500 INTERNAL_SERVER_ERROR\",\"data\":\"It is already in wishlist\"}"));
    }

    @DisplayName("헤더에 유저 정보가 있고, 이전에 찜한 기록이 없다면 Success")
    @Test
    void addWishSuccessTest() throws Exception {
        mockMvc.perform(post("/products/liked/{productId}", NEW_PRODUCT_TEST_ID)
                        .header(Constants.USER_ID_HEADER, TESTER))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().string("{\"statusCode\":\"201 CREATED\",\"data\":\"added to wishlist\"}"));
    }

    @DisplayName("찜을 하면, 상품의 조회수가 1 증가한다. (싱글 스레드)")
    @Test
    void addWishWithSingleThread() throws Exception {
        mockMvc.perform(post("/products/liked/{productId}", NEW_PRODUCT_TEST_ID)
                .header(Constants.USER_ID_HEADER, TESTER));

        Product product = productRepository.findById(NEW_PRODUCT_TEST_ID).orElseThrow(() -> new NotFoundException(ErrorCode.PRODUCT_NOT_FOUND));
        Assertions.assertEquals(1, product.getView());
    }

    @DisplayName("(멀티 스레드) 찜을 하면, 상품의 조회수가 1 증가한다.")
    @Test
    void addWishWithMultiThread() throws InterruptedException {
        int numberOfThreads = 10;
        ExecutorService service = Executors.newFixedThreadPool(numberOfThreads);
        for(int i = 0; i < numberOfThreads; i++) {
            Long tester = Long.valueOf(10000 + i);
            service.execute(() -> {
                try {
                    mockMvc.perform(post("/products/liked/{productId}", NEW_PRODUCT_TEST_ID)
                            .header(Constants.USER_ID_HEADER, tester));
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
        }

        Thread.sleep(1000);
        Product product = productRepository.findById(NEW_PRODUCT_TEST_ID).orElseThrow(() -> new NotFoundException(ErrorCode.PRODUCT_NOT_FOUND));
        Assertions.assertEquals(10, product.getView());
    }

    @DisplayName("잘못된 파라미터 page가 넘어왔을 때")
    @Test
    void invalidPageParam() throws Exception {
        mockMvc.perform(get("/products?page=" + -1 + "&size=" + 10)
                        .header(Constants.USER_ID_HEADER, TESTER))
                .andExpect(status().is4xxClientError())
                .andExpect((rslt) -> assertTrue(rslt.getResolvedException().getClass().isAssignableFrom(BindException.class)));
    }

    @DisplayName("잘못된 파라미터 Size가 넘어왔을 때")
    @Test
    void invalidSizeParam() throws Exception {
        mockMvc.perform(get("/products?page=" + 0 + "&size=" + -1)
                        .header(Constants.USER_ID_HEADER, TESTER))
                .andExpect(status().is4xxClientError())
                .andExpect((rslt) -> assertTrue(rslt.getResolvedException().getClass().isAssignableFrom(BindException.class)));
    }

    @DisplayName("잘못된 파라미터 liked가 넘어왔을 때")
    @Test
    void invalidLikedParam() throws Exception {
        mockMvc.perform(get("/products?liked=-5&page=" + 0 + "&size=" + -1)
                        .header(Constants.USER_ID_HEADER, TESTER))
                .andExpect(status().is4xxClientError())
                .andExpect((rslt) -> assertTrue(rslt.getResolvedException().getClass().isAssignableFrom(BindException.class)));
    }

    @DisplayName("쿼리 파라미터로 liked가 안왔을 때")
    @ParameterizedTest
    @MethodSource("productListParams")
    void getProductList(int page, int size, int idx, boolean isWish) throws Exception {

        mockMvc.perform(get("/products?page=" + page + "&size=" + size)
                        .header(Constants.USER_ID_HEADER, TESTER))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data[" + idx + "].liked").value(isWish))
                .andDo(print());
    }

    private static Stream<Arguments> productListParams() {
        return Stream.of(
                Arguments.of(0, 10, 0, false),
                Arguments.of(1, 10, 0, false),
                Arguments.of(2, 10, 0, false),
                Arguments.of(2, 10, 1, true), // 내가 찜한 상품
                Arguments.of(2, 10, 2, true)  // 내가 찜한 상품
        );
    }

    @DisplayName("쿼리 파라미터로 liked가 true로 넘어오면 찜한 상품만 조회한다.")
    @ParameterizedTest
    @MethodSource("wishListParams")
    void getWishList(int page, int size, int idx, boolean isWish) throws Exception {
        mockMvc.perform(get("/products?liked=true&page=" + page + "&size=" + size)
                        .header(Constants.USER_ID_HEADER, TESTER))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data", hasSize(2)))
                .andExpect(jsonPath("$.data[" + idx + "].liked").value(isWish));
    }

    private static Stream<Arguments> wishListParams() {
        return Stream.of(
                Arguments.of(0, 10, 0, true),
                Arguments.of(0, 10, 1, true)
        );
    }

    @DisplayName("쿼리 파라미터로 liked가 false로 넘어오면 찜하지 않은 상품만 조회한다.")
    @ParameterizedTest
    @MethodSource("notWishListParams")
    void getNotWishedList(int page, int size, int idx, boolean isWish) throws Exception {
        mockMvc.perform(get("/products?liked=false&page=" + page + "&size=" + size)
                        .header(Constants.USER_ID_HEADER, TESTER))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data[" + idx + "].liked").value(isWish));
    }

    private static Stream<Arguments> notWishListParams() {
        return Stream.of(
                Arguments.of(0, 10, 0, false),
                Arguments.of(1, 10, 0, false),
                Arguments.of(2, 10, 0, false)
        );
    }

}