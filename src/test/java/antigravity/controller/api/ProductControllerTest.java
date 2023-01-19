package antigravity.controller.api;

import antigravity.constant.ErrorCode;
import antigravity.payload.ProductResponse;
import antigravity.repository.ProductRepository;
import antigravity.service.ProductInfoService;
import antigravity.service.ProductRequestService;
import antigravity.service.ProductResponseService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
    private ProductResponseService productResponseService;

    @MockBean
    private ProductInfoService productInfoService;

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
                        .header("X-USER-ID", 1L)
                        .contentType(MediaType.APPLICATION_JSON)

        )
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.errorCode").value(ErrorCode.OK.getCode()))
                .andExpect(jsonPath("$.message").value(ErrorCode.OK.getMessage()));

    }

    @DisplayName("[API][POST] 찜 상품 등록 - 잘못된 정보 입력")
    @Test
    void givenWrongEvent_whenCreatingAnProduct_thenReturnsFailedStandardResponse() throws Exception {
        // Given
        String productId = "aasdfa";

        // When && Then
        mvc.perform(
                        post("/products/liked/" + productId)
                                .contentType(MediaType.APPLICATION_JSON)

                )
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.errorCode").value(ErrorCode.SPRING_BAD_REQUEST.getCode()))
                .andExpect(jsonPath("$.message").value(containsString(ErrorCode.SPRING_BAD_REQUEST.getMessage())));

    }

    @DisplayName("[API][GET] 찜 리스트 조회 + 잘못된 검색 파라미터")
    @Test
    void givenWrongParam_whenRequestProduct_thenReturnsFailedInstandardResponse() throws Exception {
        // Given
        given(productResponseService.getProducts(any(), any(), any(), any())).willReturn(List.of(createResponseDTO()));

        // When & Then
        mvc.perform(get("/products")
                        .queryParam("liked", "a")

                )
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.errorCode").value(ErrorCode.SPRING_BAD_REQUEST.getCode()))
                .andExpect(jsonPath("$.message").value(containsString(ErrorCode.SPRING_BAD_REQUEST.getMessage())));
        then(productResponseService).shouldHaveNoInteractions();
    }

    private ProductResponse createResponseDTO() {
        return ProductResponse.of(
                1L,
                "G2000000019",
                "No1. 더핏세트",
                BigDecimal.valueOf(42800.00),
                10,
                false,
                0,
                0,
                LocalDateTime.now(),
                LocalDateTime.now()
        );
    }

}