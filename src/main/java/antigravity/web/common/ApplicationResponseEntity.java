package antigravity.web.common;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ApplicationResponseEntity<T> extends ResponseEntity<BaseResponse<T>> {

    public ApplicationResponseEntity(ResponseMessages responseMessages, HttpStatus status) {
        super(new BaseResponse<>(responseMessages), status);
    }

    public ApplicationResponseEntity(ResponseMessages responseMessages, T data, HttpStatus status) {
        super(new BaseResponse<>(responseMessages, data), status);
    }
}
