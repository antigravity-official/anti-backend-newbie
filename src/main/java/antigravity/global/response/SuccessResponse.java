package antigravity.global.response;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Getter
public class SuccessResponse {

    private String successCode;
    private Object data;

    public SuccessResponse(String successCode, Object data) {
        this.successCode = successCode;
        this.data = data;
    }
}
