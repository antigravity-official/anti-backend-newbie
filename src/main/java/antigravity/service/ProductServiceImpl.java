package antigravity.service;

import antigravity.entity.Product;
import antigravity.entity.Wish;
import antigravity.global.exception.*;
import antigravity.repository.ProductRepository;
import antigravity.repository.WishRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final WishRepository wishRepository;

    @Transactional
    @Override
    public void addWish(Long userId, Long productId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new NotFoundException(ErrorCode.PRODUCT_NOT_FOUND));

        Optional<Wish> optionalWish = wishRepository.findById(userId, productId);
        if(optionalWish.isPresent()) {
            throw new BusinessException(ErrorCode.DUPLICATE_WISH_PRODUCT);
        }

        System.out.println("Thread = " + Thread.currentThread().getName());

        wishRepository.save(userId, productId);
        productRepository.updateViewCount(product);
    }
}
