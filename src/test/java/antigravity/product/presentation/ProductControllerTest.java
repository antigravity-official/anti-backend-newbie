package antigravity.product.presentation;

import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.BDDMockito.willThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import antigravity.product.application.LikeProductService;
import antigravity.user.application.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = ProductController.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private LikeProductService likeProductService;

    @Test
    @DisplayName("상품을 찜 등록할 수 있다.")
    void whenLikeProduct_thenSuccess() throws Exception {
        //given
        Long userId = 1L;
        willDoNothing().given(userService).checkUserIsActive(userId);
        Long productId = 1L;

        //expected
        mockMvc.perform(
                        post("/products/liked/{productId}", productId)
                                .header("X-USER-ID", userId)
                ).andExpect(status().isCreated());

        then(likeProductService).should().like(productId, userId);

    }

    @Test
    @DisplayName("인증 정보 없이는 상품을 찜 등록할 수 없다.")
    void givenEmptyXUSERIDHeader_whenLikeProduct_thenBadRequest() throws Exception {
        //given
        Long productId = 1L;

        //expected
        mockMvc
                .perform(
                        post("/products/liked/{productId}", productId)
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorCode").value(HttpStatus.BAD_REQUEST.toString()))
                .andExpect(jsonPath("$.errorMessage").value("인증 정보가 존재하지 않습니다."));
    }

    @Test
    @DisplayName("잘못된 인증 정보(잘못된 고객 ID)로 상품을 찜 등록할 수 없다.")
    void givenNotFoundUserByUserId_whenLikeProduct_thenBadRequest() throws Exception {
        //given
        Long userId = 0L;
        willThrow(new IllegalArgumentException("해당 고객을 찾을 수 없습니다.")).given(userService)
                .checkUserIsActive(userId);
        Long productId = 1L;

        //expected
        mockMvc.perform(
                        post("/products/liked/{productId}", productId)
                                .header("X-USER-ID", userId)
                ).andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorCode").value(HttpStatus.BAD_REQUEST.toString()))
                .andExpect(jsonPath("$.errorMessage").value("해당 고객을 찾을 수 없습니다."));
    }

    @Test
    @DisplayName("잘못된 인증 정보(삭제된 고객 ID)로 상품을 찜 등록할 수 없다.")
    void givenDeletedUserId_whenLikeProduct_thenBadRequest() throws Exception {
        //given
        Long userId = 1L;
        willThrow(new IllegalArgumentException("삭제된 고객입니다.")).given(userService)
                .checkUserIsActive(userId);
        Long productId = 1L;

        //expected
        mockMvc.perform(
                        post("/products/liked/{productId}", productId)
                                .header("X-USER-ID", userId)
                ).andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorCode").value(HttpStatus.BAD_REQUEST.toString()))
                .andExpect(jsonPath("$.errorMessage").value("삭제된 고객입니다."));
    }

}