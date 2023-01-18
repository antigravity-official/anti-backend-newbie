package antigravity.service;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class RedisService {
	private final RedisTemplate<String, String> redisTemplate;

	public Long increaseViewCount(Long productId) {
		String key = "productView::" + productId;
		ValueOperations<String, String> productView = redisTemplate.opsForValue();
		if (findProductView(productId) == null) {
			productView.set(key, "1");

			return 1L;
		}

		return productView.increment(key);
	}

	public String findProductView(Long productId) {
		ValueOperations<String, String> operations = redisTemplate.opsForValue();

		return operations.get("productView::" + productId);
	}

}
