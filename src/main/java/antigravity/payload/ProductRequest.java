package antigravity.payload;

import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.Nullable;

@Getter
@Setter
public class ProductRequest extends PagingDto{

    private Boolean liked;

    public ProductRequest() {}

    public ProductRequest(@Nullable Boolean liked, int page, int size) {
        super(page, size);
        this.liked = liked;
    }
}
