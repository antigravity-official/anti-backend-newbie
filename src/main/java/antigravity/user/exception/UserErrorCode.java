package antigravity.user.exception;

import antigravity.global.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum UserErrorCode implements ErrorCode {
    USER_ID_NOT_EXIST(HttpStatus.BAD_REQUEST, "헤더에 아이디가 존재하지 않습니다."),
    USER_NOT_EXIST(HttpStatus.BAD_REQUEST, "해당 아이디를 가진 유저가 존재하지 않습니다.");
    private final HttpStatus errorCode;
    private final String message;
}
