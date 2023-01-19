package antigravity.controller.api;

import antigravity.constant.ErrorCode;
import antigravity.repository.ProductRepository;
import antigravity.service.ProductRequestService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@DisplayName("API 컨트롤러 - 찜 상품 등록, 조회 테스트")
@WebMvcTest(ProductController.class)
class ProductControllerTest {

    private final MockMvc mvc;
    private final ObjectMapper mapper;

    @MockBean
    private ProductRequestService productRequestService;

    @MockBean
    private ProductRepository productRepository;

    public ProductControllerTest(@Autowired MockMvc mvc, @Autowired ObjectMapper mapper) {
        this.mvc = mvc;
        this.mapper = mapper;
    }

    @DisplayName("[API][POST] 찜 상품 등록 - 등록 확인 API 출력")
    @Test
    void givenNothing_whenRequestingProduct_thenReturnsTrueOrFalseResponse() throws Exception {
        // Given
        Long productId = 1L;

        // When & Then
        mvc.perform(
                post("/products/liked/" + productId)
                        .contentType(MediaType.APPLICATION_JSON)

        )
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.errorCode").value(ErrorCode.OK.getCode()))
                .andExpect(jsonPath("$.message").value(ErrorCode.OK.getMessage()));

    }

}