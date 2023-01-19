package antigravity.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

@Data
@AllArgsConstructor
public class CMResDto<T> {

    private String httpStatus;
    private String message;
    private T data;

}
