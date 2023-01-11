package antigravity.common.presentation;

import java.util.HashMap;
import java.util.Map;
import lombok.Getter;

@Getter
public class ValidationErrorResponse extends ErrorResponse {

    private final Map<String, String> validation = new HashMap<>();

    public ValidationErrorResponse(String errorCode, String errorMessage) {
        super(errorCode, errorMessage);
    }

    public void addValidation(String fieldName, String errorMessage) {
        this.validation.put(fieldName, errorMessage);
    }
}
