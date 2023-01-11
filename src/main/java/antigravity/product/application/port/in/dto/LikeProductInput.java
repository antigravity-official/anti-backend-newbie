package antigravity.product.application.port.in.dto;

import static java.util.Objects.requireNonNull;

import javax.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class LikeProductInput {

	@NotNull
	private final Long userId;
	private final Long productId;

	public LikeProductInput(Long userId, Long productId) {
		requireNonNull(userId);
		requireNonNull(productId);

		this.userId = userId;
		this.productId = productId;
	}
}
