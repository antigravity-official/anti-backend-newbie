package antigravity.product.service.impl;

import antigravity.global.redis.RedisDao;
import antigravity.product.domain.repository.ProductRepository;
import antigravity.product.service.ViewService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Iterator;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ViewServiceImpl implements ViewService {
    private final ProductRepository productRepository;
    private final RedisDao redisDao;
    @Override
    public Long addViewCntToRedis(Long productId) {
        String key = "productViewCnt::" + productId;
        if(redisDao.getData(key) == null) {
            return Long.parseLong(redisDao.setDataExpire(key, String.valueOf(productRepository.findProductViewCnt(productId) + 1), Duration.ofMinutes(5)));
        } else {
            return redisDao.incrementData(key);
        }
    }

    //3분마다 실행
    @Scheduled(cron = "0 0/3 * * * ?")
    public void deleteViewCntCacheFromRedis() {
        Set<String> redisKeys = redisDao.getKeys("productViewCnt*");
        Iterator<String> it = redisKeys.iterator();
        while(it.hasNext()) {
            String data = it.next();
            Long productId = Long.parseLong(data.split("::")[1]);
            Long viewCnt = Long.parseLong(redisDao.getData(data));
            productRepository.updateViewCntFromRedis(productId, viewCnt);
            redisDao.deleteData(data);
        }
    }
}
