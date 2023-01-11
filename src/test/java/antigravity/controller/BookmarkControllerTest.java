package antigravity.controller;


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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Transactional
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class BookmarkControllerTest {

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
}
