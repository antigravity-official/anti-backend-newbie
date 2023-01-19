package antigravity.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.MultiValueMap;

import com.fasterxml.jackson.databind.ObjectMapper;

import antigravity.controller.dto.LikeSearchDto;
import antigravity.service.CartService;

@WebMvcTest(ProductController.class)
class ProductControllerTest {

	final String URI_PREFIX = "/api/products/";
	@Autowired
	ObjectMapper objectMapper;

	@Autowired
	MockMvc mockMvc;

	@MockBean
	CartService cartService;

	@DisplayName("header에 'X-USER-ID'가 있으면 201 코드로 응답한다.")
	@Test
	void testHeader() throws Exception {
		// given
		Long productPathId = 1L;
		Integer userId = 1;

		// when
		// then
		mockMvc.perform(
			post(URI_PREFIX + "/liked/" + productPathId)
				.header("X-USER-ID", userId)
				.contentType(MediaType.APPLICATION_JSON)
		).andExpect(status().isCreated());
	}

	@DisplayName("header에 'X-USER-ID'가 없으면 400 예외가 발생한다.")
	@ParameterizedTest(name = "{index}: header 'X-USER-ID' : {0}")
	@ValueSource(strings = {"1L", "header", "-2"})
	void testFailHeader(String userId) throws Exception {
		// given
		Long productPathId = 1L;

		// when
		// then
		mockMvc.perform(
			post(URI_PREFIX + "/liked/" + productPathId)
				.header("X-USER-ID", userId)
				.contentType(MediaType.APPLICATION_JSON)
		).andExpect(status().isBadRequest());

	}

	@DisplayName("찜 상품을 조회한다.")
	@Test
	void testLike() throws Exception {
		// given
		Integer page = 1;
		Integer size = 10;
		Long productPathId = 1L;
		Integer userId = 1;

		LikeSearchDto likeSearchDto = new LikeSearchDto(page, size, null);
		MultiValueMap<String, String> params = new ParameterUtils<LikeSearchDto>()
			.toMultiValueParams(objectMapper, likeSearchDto);

		// when
		// then
		mockMvc.perform(
			get(URI_PREFIX + "/liked")
				.header("X-USER-ID", userId)
				.params(params)
				.contentType(MediaType.APPLICATION_JSON)
		).andExpect(status().isOk());

	}

	@DisplayName("페이징 파라미터들이 0보다 작거나 null 일 경우 400 예외가 발생한다.")
	@ParameterizedTest(name = "{index}: page: {0},  size: {1}")
	@MethodSource("getInvalidPagePrams")
	void testFailLike(Integer page, Integer size) throws Exception {
		// given
		Long productPathId = 1L;
		Integer userId = 1;

		LikeSearchDto likeSearchDto = new LikeSearchDto(page, size, null);
		MultiValueMap<String, String> params = new ParameterUtils<LikeSearchDto>()
			.toMultiValueParams(objectMapper, likeSearchDto);

		// when
		// then
		mockMvc.perform(
			get(URI_PREFIX + "/liked")
				.header("X-USER-ID", userId)
				.params(params)
				.contentType(MediaType.APPLICATION_JSON)
		).andExpect(status().isBadRequest());
	}


	private static Stream<Arguments> getInvalidPagePrams() {
		return Stream.of(
			Arguments.of(null, 2),
			Arguments.of(1, null),
			Arguments.of(-1, -1),
			Arguments.of(-1, 2),
			Arguments.of(3, -9)
		);
	}

}