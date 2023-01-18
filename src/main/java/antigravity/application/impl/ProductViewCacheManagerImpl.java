package antigravity.application.impl;

import antigravity.application.ProductViewCacheManager;
import antigravity.common.exception.NotFoundProductException;
import antigravity.domain.ProductView;
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
        Optional<Object> optionalCount = Optional.ofNullable(redisTemplate.opsForHash().get(PRODUCT_VIEW_KEY, String.valueOf(productId)));

        if(optionalCount.isEmpty()) {
            log.debug("cache miss");
            ProductView productView = productViewRepository.findByProductId(productId)
                    .orElseThrow(NotFoundProductException::new);
            redisTemplate.opsForHash().put(PRODUCT_VIEW_KEY, String.valueOf(productId), String.valueOf(productView.getViewCount()));
            return productView.getViewCount().intValue();
        }

        return Integer.parseInt(String.valueOf(optionalCount.get()));
    }


    @Override
    public Long incrementProductViewCount(Long productId) {
        return redisTemplate.opsForHash().increment(PRODUCT_VIEW_KEY, String.valueOf(productId), 1L);
    }

    // 1시간
    @Scheduled(cron = "0 0 0/1 * * *")
    @Transactional
    public void batchUpdateViewCount() {
        Map<Object, Object> entries = redisTemplate.opsForHash().entries(PRODUCT_VIEW_KEY);
        log.debug("BATCH UPDATE START");
        entries.keySet()
                .forEach(key -> productViewRepository.updateViewCount(
                        Long.parseLong(String.valueOf(key)),
                        Long.parseLong(String.valueOf(entries.get(key)))
                        )
                );
        log.debug("BATCH UPDATE FINISH");
    }
}
