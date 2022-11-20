package antigravity.payload;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.Nullable;

import javax.validation.constraints.AssertFalse;
import javax.validation.constraints.AssertTrue;

@Getter
@Setter
public class ProductRequest extends PagingDto{

    @Nullable
    private Boolean liked;

    public ProductRequest() {}

    public ProductRequest(@Nullable Boolean liked, int page, int size) {
        super(page, size);
        this.liked = liked;
    }
}
