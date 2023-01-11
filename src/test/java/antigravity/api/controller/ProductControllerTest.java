package antigravity.api.controller;

import antigravity.api.service.ProductLikeService;
import antigravity.api.service.ProductSearchService;
import antigravity.exception.CustomException;
import antigravity.exception.ErrorCode;
import antigravity.infra.MockMvcTest;
import antigravity.payload.request.ProductSearchRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import javax.naming.ldap.SortControl;
import javax.persistence.Entity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@MockMvcTest
class ProductControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private ProductLikeService productLikeService;

    @MockBean
    private ProductSearchService productSearchService;

    @DisplayName("POST - 찜등록(성공)")
    @Test
    void 정상적인_접근이_찜등록을_했을때_찜등록을_해준다() throws Exception {
        // given
        final Long productId = 100L;
        final Long userId = 100L;
        doNothing().when(productLikeService).addLikedProduct(productId, userId);

        // when
        final ResultActions resultActions = mockMvc.perform(
                post("/products/liked/" + productId)
                        .header("X-USER-ID", Integer.valueOf(String.valueOf(userId))));

        // then
        MvcResult mvcResult = resultActions
                .andExpect(status().isCreated()).andReturn();
        assertThat(mvcResult).isNotNull();
    }

    @DisplayName("POST - 찜등록(실패)")
    @Test
    void 정상적인_접근이_찜등록을_했지만_회원이_존재하지_않을때_예외를_터트린다() throws Exception {
        //given
        final Long productId = 100L;
        final Long userId = 100L;
        doThrow(new CustomException(ErrorCode.USER_NOT_FOUND))
                .when(productLikeService).addLikedProduct(userId,productId);

        //when
        final ResultActions resultActions = mockMvc.perform(
                post("/products/liked/" + productId)
                        .header("X-USER-ID", Integer.valueOf(String.valueOf(userId))));

        // then
        MvcResult mvcResult = resultActions
                .andExpect(status().isBadRequest()).andReturn();
        assertThat(mvcResult).isNotNull();
    }

    @DisplayName("POST - 찜등록(실패)")
    @Test
    void 정상적인_접근이_찜등록을_했지만_상품이_존재하지_않을때_예외를_터트린다() throws Exception {
        //given
        final Long productId = 100L;
        final Long userId = 100L;
        doThrow(new CustomException(ErrorCode.PRODUCT_NOT_FOUND))
                .when(productLikeService).addLikedProduct(userId,productId);

        //when
        final ResultActions resultActions = mockMvc.perform(
                post("/products/liked/" + productId)
                        .header("X-USER-ID", Integer.valueOf(String.valueOf(userId))));

        // then
        MvcResult mvcResult = resultActions
                .andExpect(status().isBadRequest()).andReturn();
        assertThat(mvcResult).isNotNull();
    }
}