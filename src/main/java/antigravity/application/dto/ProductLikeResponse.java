package antigravity.application.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class ProductLikeResponse {

    private final Long productLikeId;
    private final Long productId;
}
