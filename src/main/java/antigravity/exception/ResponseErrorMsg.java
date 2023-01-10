package antigravity.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@NoArgsConstructor
public class ResponseErrorMsg {

    private String errorMessage;
    private long code;

    public ResponseErrorMsg(String errorMessage, long code) {
        this.errorMessage = errorMessage;
        this.code = code;
    }
}
