package antigravity.global.response;

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
