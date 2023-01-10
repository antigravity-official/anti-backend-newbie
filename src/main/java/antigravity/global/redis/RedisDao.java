package antigravity.global.redis;

import antigravity.product.domain.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Repository;

import javax.persistence.SecondaryTable;
import java.time.Duration;
import java.util.Iterator;
import java.util.Set;

@RequiredArgsConstructor
@Repository
public class RedisDao {
    // String값을 key, value로 사용하는 경우 사용
    private final StringRedisTemplate stringRedisTemplate;
    public String getData(String key) {
        ValueOperations<String, String> valueOperations = stringRedisTemplate.opsForValue();
        return valueOperations.get(key);
    }
    public Set<String> getKeys(String pattern) {
        return stringRedisTemplate.keys(pattern);
    }
    public void setData(String key, String value) {
        ValueOperations<String, String> valueOperations = stringRedisTemplate.opsForValue();
        valueOperations.set(key, value);
    }

    public void setDataExpire(String key, String value, Duration duration) {
        ValueOperations<String, String> valueOperations = stringRedisTemplate.opsForValue();
        Duration expireDuration = duration;
        valueOperations.set(key, value, expireDuration);
    }
    public void incrementData(String key) {
        ValueOperations<String, String> valueOperations = stringRedisTemplate.opsForValue();
        valueOperations.increment(key);
    }

    public void deleteData(String key) {
        stringRedisTemplate.delete(key);
    }
}
