package antigravity.dto;

import javax.validation.constraints.Digits;
import lombok.Getter;


@Getter
public class LikeHistorySearchCondition {
    Boolean like;
    @Digits(integer = 10, fraction = 0)
    int size;
    @Digits(integer = 10, fraction = 0)
    int page;

    protected LikeHistorySearchCondition() {
        size = 1;
        page = 1;
    }
}
