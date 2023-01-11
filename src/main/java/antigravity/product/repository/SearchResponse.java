package antigravity.product.repository;

import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SearchResponse<T> {

    @Getter private final List<T> content;
    @Getter private final int size;
    private final int page;
    private final long total;

    public int getCurrentPage() {
        return getPageNumber() + 1;
    }

    public int getPageNumber() {
        return page;
    }

    public int getTotalPages() {
        return getSize() == 0 ? 1 : (int) Math.ceil((double) total / (double) getSize());
    }

    public boolean hasNext() {
        return getPageNumber() + 1 < getTotalPages();
    }

    public boolean isLast() {
        return !hasNext();
    }

    public long getTotalElements() {
        return total;
    }

}
