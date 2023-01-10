package antigravity.web.payload.common;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BaseResponse<T> {

    private String code;
    private String message;
    private T data;

    public BaseResponse(ResponseMessages responseMessages) {
        code = responseMessages.getCode();
        message = responseMessages.getMessage();
        data = null;
    }

    public BaseResponse(ResponseMessages responseMessages, T data) {
        code = responseMessages.getCode();
        message = responseMessages.getMessage();
        this.data = data;
    }
}
