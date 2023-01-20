package antigravity.payload.request;

import lombok.Getter;

@Getter
public class ProductSearchRequest {

    private final Boolean liked;

    public ProductSearchRequest(Boolean liked) {
        this.liked = liked;
    }
}
