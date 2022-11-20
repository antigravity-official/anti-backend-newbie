package antigravity.controller;

import antigravity.domain.repository.ProductRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


//Controller + Spring security 추가
@WebMvcTest(controllers = {ProductController.class})
class ProductControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductRepository productRepository;
    private static Long PRODUCT_TEST_ID=1L;

    @DisplayName("헤더에 유저 정보가 없는 경우")
    @Test
    void userInterceptorFailTest() throws Exception {
        mockMvc.perform(post("/products/liked/{productId}", PRODUCT_TEST_ID))
                .andExpect(status().is4xxClientError())
                .andExpect(content().string("{\"statusCode\":\"400 BAD_REQUEST\",\"data\":\"Can't find a header info\"}"));
    }

//    @DisplayName("헤더로 X-USER-ID 가 안왔을 때")
//    @Test
//    void getProductList() throws Exception {
//        mockMvc.perform(get("/products?page=" + 0 + "&size=" + 10)
//
//        @ParameterizedTest
//        @MethodSource("productListParams")
//        void getProductList(int page, int size, int idx, boolean isWish) throws Exception {
//
//            mockMvc.perform(get("/products?page=" + page + "&size=" + size)
//                            .header(Constants.USER_ID_HEADER, TESTER))
//                    .andExpect(status().isOk())
//                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                    .andExpect(jsonPath("$.data[" + idx + "].liked").value(isWish))
//                    .andDo(print());
//        }
}
