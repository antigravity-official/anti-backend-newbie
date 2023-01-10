package antigravity.product.service.impl;

import antigravity.product.domain.repository.ProductRepository;
import antigravity.product.service.ViewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ViewServiceImpl implements ViewService {
    private final ProductRepository productRepository;
    @Override
    public void addViewCntToRedis(Long productId) {

    }
}
