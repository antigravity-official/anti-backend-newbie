package antigravity.exception;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

	USER_ID_NOT_FOUND(HttpStatus.BAD_REQUEST, "User ID is not founded"),
	PRODUCT_ID_NOT_FOUND(HttpStatus.BAD_REQUEST, "Product ID is not founded"),
	ALREADY_DELETED_USER(HttpStatus.BAD_REQUEST, "Already Deleted User"),
	ALREADY_DELETED_PRODUCT(HttpStatus.BAD_REQUEST, "Already Deleted Product"),
	ALREADY_LIKED_PRODUCT(HttpStatus.BAD_REQUEST, "Already Liked Product"),
	NONEXISTENT_PRODUCTS(HttpStatus.BAD_REQUEST, "Nonexistent Products"),
	BAD_REQUEST(HttpStatus.BAD_REQUEST, "Bad request error"),
	HEADER_NULL(HttpStatus.BAD_REQUEST, "Header is null"),
	;

	private HttpStatus status;
	private String message;

}
