package antigravity.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    //예시 Exception
    EXAMPLE_EXCEPTION(HttpStatus.NOT_ACCEPTABLE, "예시 Exception");

    private final HttpStatus status;

    private final String message;
}