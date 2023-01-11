package antigravity.product.adapter.in.web;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import antigravity.product.application.port.in.GetProductsQuery;
import antigravity.product.application.port.in.LikeProductUseCase;
import antigravity.product.errors.CustomException;
import antigravity.product.errors.ErrorCode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;


@WebMvcTest(value = ProductController.class)
class ProductControllerTest {

	@Autowired
	MockMvc mockMvc;
	@Autowired
	ObjectMapper objectMapper;

	@MockBean
	LikeProductUseCase likeProductUseCase;

	@MockBean
	GetProductsQuery getProductsQuery;

	@DisplayName("찜 등록 API 성공")
	@Test
	void likeProduct_success() throws Exception {
		//given
		//when
		//then
		mockMvc.perform(post("/products/liked/1")
				.header("X-USER-ID", String.valueOf(1L))
				.contentType(MediaType.APPLICATION_JSON))
			.andDo(print())
			.andExpect(status().isCreated())
			.andExpect(jsonPath("$.message").value("상품을 찜했습니다."))
			.andExpect(jsonPath("$.data").isEmpty());
	}

	@DisplayName("찜 등록 API 실패 - 커스텀 예외 발생")
	@Test
	void likeProduct_failed_customException() throws Exception {
		//given
		doThrow(new CustomException(ErrorCode.NOT_EXISTS_USER))
			.when(likeProductUseCase).likeProduct(any());
		//when
		//then
		mockMvc.perform(post("/products/liked/1")
				.header("X-USER-ID", String.valueOf(1L))
				.contentType(MediaType.APPLICATION_JSON))
			.andDo(print())
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.message").value(ErrorCode.NOT_EXISTS_USER.getMessage()))
			.andExpect(jsonPath("$.data").isEmpty());
	}


}