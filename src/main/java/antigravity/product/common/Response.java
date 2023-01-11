package antigravity.product.common;

import lombok.Getter;

@Getter
public class Response<T> {

	private final String message;
	private T data;

	private Response(String message, T data) {
		this.message = message;
		this.data = data;
	}

	private Response(String message) {
		this.message = message;
	}

	public static <T> Response<T> success(String message, T data) {
		return new Response<>(message, data);
	}

	public static <T> Response<T> success(String message) {
		return new Response<>(message, null);
	}

	public static <T> Response<T> fail(String message) {
		return new Response<>(message, null);
	}


}
