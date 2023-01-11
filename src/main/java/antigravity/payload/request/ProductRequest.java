package antigravity.payload.request;

import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
public class ProductRequest {

    @NotNull
    private final Long productId;

    public ProductRequest(Long productId) {
        this.productId = productId;
    }
}
