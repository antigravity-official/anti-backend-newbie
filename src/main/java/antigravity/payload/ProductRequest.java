package antigravity.payload;

import lombok.Data;
import org.springframework.lang.Nullable;

import javax.validation.constraints.AssertFalse;
import javax.validation.constraints.AssertTrue;

@Data
public class ProductRequest extends PagingDto{

    @Nullable
    @AssertFalse
    @AssertTrue
    private Boolean liked;

    public ProductRequest() {}

    public ProductRequest(@Nullable Boolean liked, int page, int size) {
        super(page, size);
        this.liked = liked;
    }
}
