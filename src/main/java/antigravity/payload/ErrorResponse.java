package antigravity.payload;

import java.util.HashMap;
import java.util.Map;

import lombok.Builder;

/**
 * ex)
 * {
 *     "code": "400",
 *     "message": "잘못된 요청입니다.",
 *	   "validation": {
 *	  		"title" : "값이 비어있을 수 없습니다."
 *       }
 * }
 */
public class ErrorResponse {

	private final String code;
	private final String message;
	private final Map<String, String> validation;

	@Builder
	public ErrorResponse(String code, String message, Map<String, String> validation) {
		this.code = code;
		this.message = message;
		this.validation = validation != null ? validation : new HashMap<>();
	}

	public void addValidation(String fieldName, String errorMessage) {
		this.validation.put(fieldName, errorMessage);
	}
}
