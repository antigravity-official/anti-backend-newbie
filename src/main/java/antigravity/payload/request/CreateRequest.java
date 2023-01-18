package antigravity.payload.request;

import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
public class CreateRequest {
    @NotNull
    private final Long productId;

    public CreateRequest(Long productId) {
        this.productId = productId;
    }
}
