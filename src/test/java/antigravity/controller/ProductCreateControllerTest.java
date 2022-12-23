package antigravity.controller;

import antigravity.entity.LikedStatus;
import antigravity.entity.dto.LikedDto;
import antigravity.entity.dto.LikedDto.Create.Response;
import antigravity.entity.dto.exception.BadRequestException;
import antigravity.service.LikedCreator;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static io.github.benas.randombeans.api.EnhancedRandom.random;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("찜 상품 생성 테스트")
@WebMvcTest(controllers = ProductController.class)
class ProductCreateControllerTest {
    public String BASE_URL = String.format("http://%s:8089/products", "localhost");

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    LikedCreator creator;

    @Test
    @DisplayName("생성")
    void testSuccessCreate() throws Exception {
        Response response = random(Response.class);
        given(creator.create(any())).willReturn(response);

        mockMvc.perform(post(String.format("%s/liked/%s", BASE_URL, 1))
                                .header("X-USER-ID", 2))
               .andExpect(status().isCreated())
               .andExpect(jsonPath("$.likedId").exists())
               .andExpect(jsonPath("$.productId").exists())
               .andExpect(jsonPath("$.memberId").exists())
               .andExpect(jsonPath("$.likedStatus").exists())
        ;

        verify(creator).create(any());
        verifyNoMoreInteractions(creator);
    }

    @Test
    @DisplayName("탈퇴한 회원")
    void testFailureCreateExpireMember() throws Exception {

        doThrow(BadRequestException.class).when(creator).create(any());

        mockMvc.perform(post(String.format("%s/liked/%s", BASE_URL, 1))
                                .header("X-USER-ID", 1)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(new ObjectMapper().writeValueAsString(new LikedDto.Create.Request(LikedStatus.LIKED))))
               .andExpect(status().isBadRequest())
        ;
    }

    @Test
    @DisplayName("삭제된 상품")
    void testFailureCreateDeletedProduct() throws Exception {
        doThrow(BadRequestException.class).when(creator).create(any());

        mockMvc.perform(post(String.format("%s/liked/%s", BASE_URL, 333))
                                .header("X-USER-ID", 2)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(new ObjectMapper().writeValueAsString(new LikedDto.Create.Request(LikedStatus.LIKED))))
               .andExpect(status().isBadRequest())
        ;
    }
}
