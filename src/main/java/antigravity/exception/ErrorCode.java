package antigravity.exception;
import lombok.Getter;

@Getter
public enum ErrorCode {
    USER_NOT_FOUND(400, "C001", "The User dose not exist."),
    PRODUCT_NOT_FOUND(400, "C002", "The product dose not exist."),
    INTERNAL_SERVER_ERROR(500, "S000", "Internal server error.");

    private final String code;
    private final String message;
    private final int status;

    ErrorCode(final int status, final String code, final String message) {
        this.status = status;
        this.message = message;
        this.code = code;
    }
}
