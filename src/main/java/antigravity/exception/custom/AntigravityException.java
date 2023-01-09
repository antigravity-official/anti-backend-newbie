package antigravity.exception.custom;

import java.util.HashMap;
import java.util.Map;

import lombok.Getter;

@Getter
public abstract class AntigravityException extends RuntimeException {

	public final Map<String, String> validation = new HashMap<>();

	public AntigravityException(String message) {
		super(message);
	}

	public abstract int getStatusCode();

	public void addValidation(String fieldName, String message) {
		validation.put(fieldName, message);
	}
}
