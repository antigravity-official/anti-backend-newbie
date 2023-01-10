package antigravity.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import antigravity.exception.GlobalExceptionHandler;
import antigravity.service.ProductLikeService;
import antigravity.service.ProductService;

@AutoConfigureMockMvc
@SpringBootTest
public class ProductControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private ProductLikeService productLikeService;

	@MockBean
	private ProductService productService;

	@DisplayName("[API][POST] 찜 등록")
	@Test
	void given_ProductIdAndUserId_when_AddLikedProduct_then_ReturnCreated() throws Exception {
		//given
		Long productId = 1L;
		Long userId = 2L;

		//mock
		doNothing().when(productLikeService).productLike(productId, userId);

		//when&then
		mockMvc.perform(
				post("/products/liked/" + productId)
					.header("X-USER-ID", String.valueOf(userId)))
			.andExpect(status().isCreated())
			.andDo(print());
	}

	@DisplayName("[API][POST] 찜 등록 - 비즈니스 로직 실행 중 에러 발생")
	@Test
	void given_ProductIdAndUserId_when_AddLikedProduct_then_ReturnBadRequest() throws Exception {
		//given
		Long productId = 1L;
		Long userId = 2L;

		//mock
		doThrow(new IllegalStateException()).when(productLikeService).productLike(productId, userId);

		//when&then
		mockMvc.perform(
				post("/products/liked/" + productId)
					.header("X-USER-ID", String.valueOf(userId)))
			.andExpect(status().isBadRequest())
			.andDo(print());
	}

	@DisplayName("[API][POST] 찜 등록 - X-USER-ID 헤더가 없을 때 에러 발생")
	@Test
	void given_NonHeader_when_AddLikedProduct_then_ReturnBadRequest() throws Exception {
		//given
		Long productId = 1L;

		//mock

		//when&then
		mockMvc.perform(
				post("/products/liked/" + productId))
			.andExpect(status().isBadRequest())
			.andDo(print());
	}

	@DisplayName("[API][GET] 상품 조회")
	@Test
	void given_UserId_when_GetProducts_then_ReturnOk() throws Exception {
		//given
		Long userId = 2L;
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("page", "0");
		params.add("size", "10");

		//mock
		when(productService.getProducts(userId, mock(Pageable.class), null))
			.thenReturn(mock(Page.class));

		//when&then
		mockMvc.perform(
				get("/products/liked")
					.header("X-USER-ID", String.valueOf(userId))
					.params(params)
			)
			.andExpect(status().isOk())
			.andDo(print());
	}

	@DisplayName("[API][GET] 상품 조회 - X-USER-ID 헤더가 존재하지 않을 경우")
	@Test
	void given_NonHeader_when_GetProducts_then_ReturnBadRequest() throws Exception {
		//given
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("page", "0");
		params.add("size", "10");

		//mock

		//when&then
		mockMvc.perform(
				get("/products/liked")
					.params(params)
			)
			.andExpect(status().isBadRequest())
			.andDo(print());
	}

	@DisplayName("[API][GET] 상품 조회 - 비즈니스 로직 실행중 에러 발생")
	@Test
	void given_UserId_when_GetProducts_then_ReturnBadRequest() throws Exception {
		//given
		Long userId = 2L;
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("page", "0");
		params.add("size", "10");

		//mock
		when(productService.getProducts(userId, mock(Pageable.class), true))
			.thenThrow(IllegalStateException.class);
		mockMvc = MockMvcBuilders.standaloneSetup(new ProductController(productService, productLikeService))
			.setControllerAdvice(GlobalExceptionHandler.class).build();

		//when&then
		mockMvc.perform(
				get("/products/liked")
					.header("X-USER-ID", String.valueOf(userId))
					.params(params)
			)
			.andExpect(status().isBadRequest())
			.andDo(print());
	}

}
