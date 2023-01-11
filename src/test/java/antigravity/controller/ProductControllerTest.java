package antigravity.controller;

import antigravity.config.PageParam;
import antigravity.payload.ProductResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebAppConfiguration
class ProductControllerTest {

    @Autowired
    private ProductController productController;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void likedCreateFall() {
        Long userId = 1L;
        Long productId = 3L;
        ResponseEntity value = productController.likedCreate(userId, productId);

        Assertions.assertEquals("회원 정보가 존재하지 않습니다.", value.getBody());
    }

    @Test
    void likedCreate(){
        Long userId = 2L;
        Long productId = 3L;
        ResponseEntity value = productController.likedCreate(userId, productId);
        Assertions.assertEquals("찜 등록이 완료 되었습니다.", value.getBody());
    }

    @Test
    void productView() throws Exception {

        mockMvc.perform(get("/products?liked=true&page=1&size=10").header("X-USER-ID",3))
                .andExpect(status().isOk())
                .andDo(print());
    }

}