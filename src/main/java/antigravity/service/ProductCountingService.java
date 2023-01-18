package antigravity.service;

import java.util.Objects;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ProductCountingService {
	private final RedisTemplate<String, String> redisTemplate;

	public Long increaseViewCount(Long productId, Long userId) {
		String viewCountKey = "productViewCount::" + productId;
		String viewerKey = "productViewer::" + productId;

		SetOperations<String, String> viewers = redisTemplate.opsForSet();

		Boolean isViewer = viewers.isMember(viewerKey, userId.toString());

		if (Boolean.TRUE.equals(isViewer)) {
			return findProductViewCount(productId);
		}

		viewers.add(viewerKey, userId.toString());
		ValueOperations<String, String> productView = redisTemplate.opsForValue();

		if (findProductViewCount(productId) == 0L) {
			Long initialValue = 1L;
			productView.set(viewCountKey, initialValue.toString());

			return initialValue;
		}

		return productView.increment(viewCountKey);
	}

	public Long findProductViewCount(Long productId) {
		ValueOperations<String, String> operations = redisTemplate.opsForValue();
		String key = "productViewCount::" + productId;

		if (operations.get(key) == null) {
			return 0L;
		}

		return Long.parseLong(
			Objects.requireNonNull(operations.get(key))
		);
	}

	public Long increaseLike(Long productId) {
		String key = "productLike::" + productId;
		ValueOperations<String, String> operations = redisTemplate.opsForValue();

		if (findProductLikeCount(productId) == 0L) {
			Long initialValue = 1L;
			operations.set(key, "1");

			return initialValue;
		}

		return operations.increment(key);
	}

	public Long findProductLikeCount(Long productId) {
		ValueOperations<String, String> operations = redisTemplate.opsForValue();
		String key = "productLike::" + productId;

		if (operations.get(key) == null) {
			return 0L;
		}

		return Long.parseLong(
			Objects.requireNonNull(operations.get(key))
		);
	}
}