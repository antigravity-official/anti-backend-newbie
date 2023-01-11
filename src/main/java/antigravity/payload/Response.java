package antigravity.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Response<T> {

	private String resultCode;
	private T result;

	public static <T> Response<T> error(String errorCode) {
		return new Response<>(errorCode, null);
	}

	public static <T> Response<T> success(T result) {
		return new Response<>("success", result);
	}

	public static Response<Void> success() {
		return new Response<>("success", null);
	}

}
