package antigravity.payload.request;

import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
public class ProductLikedRequest {

    @NotNull
    private final Long productId;

    public ProductLikedRequest(Long productId) {
        this.productId = productId;
    }
}
