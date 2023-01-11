package antigravity.payload.request;

import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
public class ProductSearchRequest {

    private final Boolean liked;

    public ProductSearchRequest(Boolean liked) {
        this.liked = liked;
    }
}
