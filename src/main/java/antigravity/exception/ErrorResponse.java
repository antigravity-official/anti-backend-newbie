package antigravity.exception;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.transaction.annotation.Transactional;

@Builder
@Getter
@ToString
public class ErrorResponse {
    private String message;
    private Integer status;
    private String errorCode;
}
