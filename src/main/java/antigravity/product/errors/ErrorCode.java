package antigravity.product.errors;


import static org.springframework.http.HttpStatus.*;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
	NOT_EXISTS_USER(BAD_REQUEST,"존재하지 않는 회원입니다."),
	NOT_EXISTS_PRODUCT(BAD_REQUEST,"존재하지 않는 상품입니다."),
	ALREADY_LIKED_PRODUCT(BAD_REQUEST, "이미 찜한 상품입니다.")
	;

	private final HttpStatus status;
	private final String message;
}
