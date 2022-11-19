package antigravity.controller;

import antigravity.global.common.Constants;
import antigravity.global.exception.DuplicatedEntityException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * ### 찜 상품 등록
 * [POST] `/products/liked/{productId}`
 * {user}가 {productId}를 찜 했다는 정보를 저장합니다.
 * {user}가 찜을 할 때마다 **상품 조회 수**도 1 증가합니다.
 * {user}가 존재하지 않거나 잘못된 {productId}로 요청을 했거나 이미 찜한 상품일 경우 `400 Bad Request` 로 응답합니다.
 * 정상적으로 등록이 완료되면 `201 Created` 로 응답하며, 응답 본문은 자유롭게 구현할 수 있습니다.
 */
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class ProductControllerTest {

    @Autowired
    MockMvc mockMvc;

    @DisplayName("헤더에 유저 정보가 없는 경우")
    @Test
    void userInterceptorFailTest() throws Exception {
        Long testProductId = 99999L;
        mockMvc.perform(post("/products/liked/{productId}", testProductId))
                .andExpect(status().is4xxClientError())
                .andExpect(content().string("{\"errorCode\":\"400 BAD_REQUEST\",\"errorMessage\":\"Can't find a header info\"}"));
    }

    @DisplayName("헤더에 유저 정보가 있고, 이미 찜한 500(DuplicatedEntityException) 발생")
    @Test
    void addWishFailTest() throws Exception {
        Long tester = 99998L;
        Long testProductId = 99999L;
        ResultActions resultActions = mockMvc.perform(post("/products/liked/{productId}", testProductId)
                        .header(Constants.USER_ID_HEADER, tester))
                .andExpect(status().is4xxClientError())
                .andExpect(content().string("{\"errorCode\":\"500 INTERNAL_SERVER_ERROR\",\"errorMessage\":\"It is already in wishlist\"}"));
    }

    @DisplayName("헤더에 유저 정보가 있고, 이전에 찜한 기록이 없다면 Success")
    @Test
    void addWishSuccessTest() throws Exception {
        Long tester = 99998L;
        Long testProductId = 99990L;
        ResultActions resultActions = mockMvc.perform(post("/products/liked/{productId}", testProductId)
                        .header(Constants.USER_ID_HEADER, tester))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().string("{\"successCode\":\"201 CREATED\",\"data\":\"added to wishlist\"}"));
    }
}