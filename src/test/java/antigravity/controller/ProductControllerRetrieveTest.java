package antigravity.controller;

import antigravity.payload.ProductConstants;
import antigravity.payload.ProductResponse;
import antigravity.service.LikedCreator;
import antigravity.service.LikedRetriever;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.web.servlet.MockMvc;

import static io.github.benas.randombeans.api.EnhancedRandom.randomListOf;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@DisplayName("찜 상품 조회 테스트")
@WebMvcTest(ProductController.class)
class ProductControllerRetrieveTest {

    public static final String BASE_URL = "http://localhost:8089/products/liked";
    @Autowired
    MockMvc mockMvc;

    @MockBean
    LikedRetriever likedRetriever;

    @MockBean
    LikedCreator _notUsed;

    PageRequest defaultPageable = PageRequest.of(0, 10);

    @Test
    @DisplayName("찜 목록 조회")
    void test() throws Exception {
        PageImpl<ProductResponse> response = new PageImpl<>(randomListOf(10, ProductResponse.class));
        given(likedRetriever.products(any())).willReturn(response);

        mockMvc.perform(get(BASE_URL).param("liked", "true")
                                     .param("page", "0")
                                     .param("size", "10")
                                     .header(ProductConstants.X_USER_ID_KEY, 1L))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.pageable").exists())
               .andExpect(jsonPath("$.totalPages").exists())
               .andExpect(jsonPath("$.totalElements").exists())
               .andExpect(jsonPath("$.size").exists())
               .andExpect(jsonPath("$.content").isArray())
               .andExpect(jsonPath("$.content[0].id").exists())
               .andExpect(jsonPath("$.content[0].sku").exists())
               .andExpect(jsonPath("$.content[0].name").exists())
               .andExpect(jsonPath("$.content[0].price").exists())
               .andExpect(jsonPath("$.content[0].quantity").exists())
               .andExpect(jsonPath("$.content[0].liked").exists())
               .andExpect(jsonPath("$.content[0].totalLiked").exists())
               .andExpect(jsonPath("$.content[0].viewed").exists())
               .andExpect(jsonPath("$.content[0].createdAt").exists())
               .andExpect(jsonPath("$.content[0].updatedAt").exists())
               .andDo(print())
        ;

        verify(likedRetriever).products(any());
        verifyNoMoreInteractions(likedRetriever);
        verifyNoInteractions(_notUsed);
    }
}
