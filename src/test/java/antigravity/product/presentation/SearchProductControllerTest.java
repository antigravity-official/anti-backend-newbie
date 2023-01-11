package antigravity.product.presentation;

import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.BDDMockito.willThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import antigravity.product.application.SearchProductService;
import antigravity.user.application.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = SearchProductController.class)
class SearchProductControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private SearchProductService searchProductService;

    @Test
    @DisplayName("전체 상품 목록을 조회할 수 있다.")
    void whenSearchProductWithUser_thenAllProducts() throws Exception {
        //given
        Long userId = 1L;
        willDoNothing().given(userService).checkUserIsActive(userId);

        //expected
        mockMvc.perform(
                get("/products/liked")
                        .header("X-USER-ID", userId)
                )
                .andExpect(status().isOk());

        then(searchProductService).should().searchProduct(null, userId, 1, 10);
    }

    @Test
    @DisplayName("찜한 상품 목록을 조회할 수 있다.")
    void givenLikedIsTrue_whenSearchProductWithUser_thenProductsWithLiked() throws Exception {
        //given
        Long userId = 1L;
        willDoNothing().given(userService).checkUserIsActive(userId);
        Boolean liked = true;

        //expected
        mockMvc.perform(
                        get("/products/liked")
                                .header("X-USER-ID", userId)
                                .param("liked", String.valueOf(liked))
                )
                .andExpect(status().isOk());

        then(searchProductService).should().searchProduct(liked, userId, 1, 10);
    }

    @Test
    @DisplayName("page는 1 이상만 가능하다.")
    void givenPageIsNegativeOrZero_whenSearchProductWithUser_thenBadRequest() throws Exception {
        Long userId = 1L;
        willDoNothing().given(userService).checkUserIsActive(userId);
        int page = 0;

        mockMvc.perform(
                        get("/products/liked")
                                .header("X-USER-ID", userId)
                                .param("page", String.valueOf(page))
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorCode").value(HttpStatus.BAD_REQUEST.toString()))
                .andExpect(jsonPath("$.errorMessage").value("유효성 검사에 실패했습니다."))
                .andExpect(jsonPath("$.validation.page").value("페이지는 1부터 입력할 수 있습니다."))
        ;
    }

    @Test
    @DisplayName("size는  1이상 50이하만 가능하다.")
    void givenSizeIs51_whenSearchProductWithUser_thenBadRequest() throws Exception {
        Long userId = 1L;
        willDoNothing().given(userService).checkUserIsActive(userId);
        int size = 51;

        mockMvc.perform(
                        get("/products/liked")
                                .header("X-USER-ID", userId)
                                .param("size", String.valueOf(size))
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorCode").value(HttpStatus.BAD_REQUEST.toString()))
                .andExpect(jsonPath("$.errorMessage").value("유효성 검사에 실패했습니다."))
                .andExpect(jsonPath("$.validation.size").value("size는 1부터 50까지 입력 가능합니다."))
        ;
    }



    @Test
    @DisplayName("인증 정보 없이 상품 목록을 조회할 수 없다.")
    void givenEmptyXUSERIDHeader_whenSearchProductWithUser_thenBadRequest() throws Exception {
        //given
        int size = 11;

        //expected
        mockMvc.perform(
                        get("/products/liked")
                                .param("size", String.valueOf(size))
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorCode").value(HttpStatus.BAD_REQUEST.toString()))
                .andExpect(jsonPath("$.errorMessage").value("인증 정보가 존재하지 않습니다."));
    }

    @Test
    @DisplayName("잘못된 인증 정보(잘못된 고객 ID)로 상품 목록을 조회할 수 없다.")
    void givenNotFoundUserByUserId_whenSearchProductWithUser_thenBadRequest() throws Exception {
        //given
        Long userId = 0L;
        Boolean liked = true;
        willThrow(new IllegalArgumentException("해당 고객을 찾을 수 없습니다.")).given(userService)
                .checkUserIsActive(userId);

        //expected
        mockMvc.perform(
                        get("/products/liked")
                                .header("X-USER-ID", userId)
                                .param("liked", String.valueOf(liked))
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorCode").value(HttpStatus.BAD_REQUEST.toString()))
                .andExpect(jsonPath("$.errorMessage").value("해당 고객을 찾을 수 없습니다."));

    }

    @Test
    @DisplayName("잘못된 인증 정보(삭제된 고객 ID)로 상품 목록을 조회할 수 없다.")
    void givenDeletedUserId_whenSearchProductWithUser_thenBadRequest() throws Exception {
        //given
        Long userId = 1L;
        willThrow(new IllegalArgumentException("삭제된 고객입니다.")).given(userService)
                .checkUserIsActive(userId);

        //expected
        mockMvc.perform(
                        get("/products/liked")
                                .header("X-USER-ID", userId)
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorCode").value(HttpStatus.BAD_REQUEST.toString()))
                .andExpect(jsonPath("$.errorMessage").value("삭제된 고객입니다."));
    }

}