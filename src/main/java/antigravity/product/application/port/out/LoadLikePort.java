package antigravity.product.application.port.out;

import antigravity.product.domain.Like;
import java.util.Optional;

public interface LoadLikePort {

	Optional<Like> getLike(Long userId, Long productId);

}
