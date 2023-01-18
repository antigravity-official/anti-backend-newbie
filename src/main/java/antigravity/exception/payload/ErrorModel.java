package antigravity.exception.payload;

import org.springframework.http.HttpStatus;

public interface ErrorModel {
	String getCode();

	String getMessage();

	HttpStatus getStatus();
}

