package antigravity.payload;

import lombok.Data;

@Data
public class ProductSearchCriteria {
    private Long userId;
    private Boolean liked;
    private int page;
    private int size;

    public ProductSearchCriteria(Integer page, Integer size) {
        this.page = page == null ? 0 : page;
        this.size = size == null ? 10 : size;
    }
}
