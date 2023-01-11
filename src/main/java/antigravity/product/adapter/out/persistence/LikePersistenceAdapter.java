package antigravity.product.adapter.out.persistence;

import antigravity.product.application.port.out.LoadLikePort;
import antigravity.product.domain.Like;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
class LikePersistenceAdapter implements LoadLikePort {

	private final LikeRepository likeRepository;

	@Override
	public Optional<Like> getLike(Long userId, Long productId) {
		return likeRepository.findByUserIdAndProductId(userId, productId);
	}
}
