package antigravity.product.application.port.in.dto;

import static java.util.Objects.requireNonNull;

import lombok.Getter;
import org.springframework.data.domain.Pageable;

@Getter
public class GetProductsInput {
	private final Long userId;
	private final Boolean liked;
	private final Pageable pageable;

	public GetProductsInput(Long userId, Boolean liked, Pageable pageable) {
		requireNonNull(userId);

		this.userId = userId;
		this.liked = liked;
		this.pageable = pageable;
	}
}
