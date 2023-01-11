package antigravity.dto;

import javax.validation.constraints.Digits;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class LikeHistorySearchCondition {
    Boolean liked;
    @Digits(integer = 10, fraction = 0)
    int size;
    @Digits(integer = 10, fraction = 0)
    int page;

    protected LikeHistorySearchCondition() {
        size = 1;
        page = 0;
    }
}
