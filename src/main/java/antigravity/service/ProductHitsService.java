package antigravity.service;

import antigravity.entity.ProductHits;
import antigravity.repository.ProductHitsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductHitsService {
    private final ProductHitsRepository productHitsRepository;
    private final StringRedisTemplate redisTemplate;
    private static final String KEY_PATTERN = "product:hits:%s";

    @Transactional
    public void increaseHits(Long productId) {
        try {
            ValueOperations<String, String> operation = redisTemplate.opsForValue();
            String redisKey = makeRedisKey(productId);
            operation.increment(redisKey);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    @Scheduled(cron="0 */10 * * * *")
    public void scheduledWriteProductHitToRedis() {
        try(RedisConnection redisConnection = redisTemplate.getConnectionFactory().getConnection()) {
            ScanOptions options = ScanOptions.scanOptions().match(makeRedisScanKey()).count(10).build();
            Cursor<byte[]> cursor = redisConnection.scan(options);
            while (cursor.hasNext()) {
                updateProductHitToDataBase(new String(cursor.next()));
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    private String makeRedisKey(Long productId) {
        return String.format(KEY_PATTERN, productId);
    }

    private String makeRedisScanKey() {
        return String.format(KEY_PATTERN, "*");
    }

    @Transactional
    private void updateProductHitToDataBase(String key) {
        String hitsCount = redisTemplate.opsForValue().get(key);
        redisTemplate.delete(key);
        String productId = key.replaceAll("[^0-9]", "");
        ProductHits productHits = productHitsRepository.findByProductId(Long.valueOf(productId));
        productHits.increaseHit(Long.valueOf(hitsCount));
        productHitsRepository.save(productHits);
    }

    @Transactional(readOnly = true)
    public List<ProductHits> getProductHit(Long[] targetProductIds) {
        return productHitsRepository.findByProduct(targetProductIds);
    }
}
