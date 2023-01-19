package antigravity.controller;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ParameterUtils<T> {
	public MultiValueMap<String, String> toMultiValueParams(ObjectMapper objectMapper, T request) {
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();

		params.setAll(objectMapper.convertValue(
			request,
			new TypeReference<>() {
			}
		));

		return params;
	}
}
