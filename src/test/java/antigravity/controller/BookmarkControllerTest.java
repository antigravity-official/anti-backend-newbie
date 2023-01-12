package antigravity.controller;

import antigravity.repository.BookmarkRepository;
import antigravity.repository.ProductHitsRepository;
import antigravity.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Transactional
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class BookmarkControllerTest {

    @Autowired
    private BookmarkRepository bookmarkRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductHitsRepository productHitsRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext ctx;

    private final String BASE_URL = "/products";

    @BeforeEach()
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(ctx)
                .addFilters(new CharacterEncodingFilter("UTF-8", true))  // 필터 추가
                .alwaysDo(print())
                .build();
    }

    @Test
    @DisplayName("찜 등록 성공 테스트")
    public void userBookmarkCreateSuccessTest() throws Exception {
        Integer userId = 1;
        Long productId = 1L;

        mockMvc.perform(post(BASE_URL+"/liked/"+productId)
                .header("X-USER-ID",userId))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("찜 등록 헤더 요청 실패 테스트")
    public void userBookmarkCreateFailTest() throws Exception {
        Long productId = 1L;

        mockMvc.perform(post(BASE_URL+"/liked/"+productId))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("찜 상품 liked true 조회")
    public void userBookmarkGetLikedTrueSuccessTest() throws Exception {
        Boolean liked = true;
        Integer userId = 1;

        mockMvc.perform(get(BASE_URL+"/liked/"+liked)
                .header("X-USER-ID",userId)
                .param("page","0")
                .param("size","2"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("찜 상품 liked false 조회")
    public void userBookmarkGetLikedFalseSuccessTest() throws Exception {
        Boolean liked = false;
        Integer userId = 1;

        mockMvc.perform(get(BASE_URL+"/liked/"+liked)
                        .header("X-USER-ID",userId)
                        .param("page","0")
                        .param("size","2"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("찜 상품 liked null 조회")
    public void userBookmarkGetLikedNullSuccessTest() throws Exception {
        Boolean liked = null;
        Integer userId = 1;

        mockMvc.perform(get(BASE_URL+"/liked/"+liked)
                        .header("X-USER-ID",userId)
                        .param("page","0")
                        .param("size","2"))
                .andExpect(status().isOk());
    }
}
