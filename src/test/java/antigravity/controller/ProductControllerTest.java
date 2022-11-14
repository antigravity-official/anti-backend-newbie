package antigravity.controller;

import antigravity.payload.ProductSearchCriteria;
import antigravity.service.ProductLikeService;
import antigravity.service.ProductService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@WebMvcTest(ProductController.class)
class ProductControllerTest {
    @MockBean ProductService productService;
    @MockBean ProductLikeService productLikeService;
    @Autowired MockMvc mockMvc;

    @Test
    @DisplayName("X-USER-ID 헤더가 없을시 401 에러 발생")
    void interceptor_test() throws Exception {
        //given
        Long productId = 1L;

        //when & then
        mockMvc.perform(post("/products/liked/{productId}", productId))
                //mvc.perform()의 결과를 검증
                .andExpect(status().is4xxClientError()) //401
                .andExpect(content().string("{\"message\":\"Add header then retry...\",\"status\":401}"));
    }

    @Test
    @DisplayName("X-USER-ID 헤더가 있을시엔 통과")
    void interceptor_test2() throws Exception {
        //given
        Long productId = 1L;
        Long xUserIdHeader = 1L;

        //when & then
        mockMvc.perform(post("/products/liked/{productId}", productId)
                        .header("X-USER-ID", xUserIdHeader))
                .andExpect(status().is2xxSuccessful()); //200 상태

        mockMvc.perform(get("/products").requestAttr("productSearchCriteria", new ProductSearchCriteria(0,10))
                        .header("X-USER-ID", xUserIdHeader))
                .andExpect(status().is2xxSuccessful()); //200 상태
    }
}