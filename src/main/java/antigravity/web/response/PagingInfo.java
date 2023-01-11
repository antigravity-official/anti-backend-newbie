package antigravity.web.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PagingInfo {

    private long totalElements;
    private int totalPage;
    private boolean hasNext;
    private boolean isLast;

    public PagingInfo(long totalElements, int totalPage, boolean hasNext, boolean isLast) {
        this.totalElements = totalElements;
        this.totalPage = totalPage;
        this.hasNext = hasNext;
        this.isLast = isLast;
    }
}
