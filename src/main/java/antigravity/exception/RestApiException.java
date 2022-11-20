package antigravity.exception;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@NoArgsConstructor
public class RestApiException {

    private String errorMessage;
    private boolean resultFlag;
    private long code;

    private HttpStatus httpStatus;

    @Builder
    public RestApiException(String errorMessage, boolean resultFlag, long code, HttpStatus httpStatus){
        this.errorMessage =errorMessage;
        this.resultFlag =resultFlag;
        this.code =code;
        this.httpStatus = httpStatus;
    }
}
