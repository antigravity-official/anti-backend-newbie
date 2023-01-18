package antigravity.application.impl;

import antigravity.application.ProductViewCacheManager;
import antigravity.domain.repository.ProductViewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class ProductViewCacheManagerImpl implements ProductViewCacheManager {

    private static final String PRODUCT_VIEW_KEY = "productView";
    private final RedisTemplate<String, Object> redisTemplate;
    private final ProductViewRepository productViewRepository;


    @Override
    public Integer getViewCount(Long productId) {
        Optional<Object> optionalCount = Optional.ofNullable(redisTemplate.opsForHash().get(PRODUCT_VIEW_KEY, productId));

        if(optionalCount.isEmpty()) {
            Long viewCount = productViewRepository.findCountById(productId);
            redisTemplate.opsForHash().put(PRODUCT_VIEW_KEY, productId, viewCount);
            return viewCount.intValue();
        }

        return (Integer) optionalCount.get();
    }


    @Override
    public void incrementProductViewCount(Long productId) {
        redisTemplate.opsForHash().increment(PRODUCT_VIEW_KEY, productId, 1L);
    }

    @Scheduled(fixedRate = 1000)
    public void batchUpdateViewCount() {
        Map<Object, Object> entries = redisTemplate.opsForHash().entries(PRODUCT_VIEW_KEY);
        log.info("BATCH UPDATE START");
        entries.keySet()
                .forEach(key -> productViewRepository.updateViewCount((Long) key, (Long) entries.get(key)));
        log.info("BATCH UPDATE FINISH");
    }
}
