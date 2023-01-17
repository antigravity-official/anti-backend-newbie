package antigravity.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public class ResponseMessage {
    private String statusCode;

    private HttpStatus status;

    private String message;
}
