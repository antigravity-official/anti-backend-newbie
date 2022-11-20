package antigravity.service;

import antigravity.entity.Product;
import antigravity.entity.Wish;
import antigravity.global.exception.BusinessException;
import antigravity.global.common.ErrorCode;
import antigravity.global.exception.NotFoundException;
import antigravity.payload.ProductRequest;
import antigravity.payload.ProductResponse;
import antigravity.repository.JdbcProductRepository;
import antigravity.repository.JdbcWishRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static antigravity.payload.ProductResponse.toProductResponse;

@Slf4j
@RequiredArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {

    private final JdbcProductRepository jdbcProductRepository;
    private final JdbcWishRepository jdbcWishRepository;

    @Transactional
    @Override
    public void addWish(Long userId, Long productId) {
        Product product = jdbcProductRepository.findById(productId).orElseThrow(() -> new NotFoundException(ErrorCode.PRODUCT_NOT_FOUND));

        Optional<Wish> optionalWish = jdbcWishRepository.findById(userId, productId);
        if(optionalWish.isPresent()) {
            throw new BusinessException(ErrorCode.DUPLICATE_WISH_PRODUCT);
        }

        jdbcWishRepository.save(userId, productId);

        jdbcProductRepository.updateViewCount(product);
    }

    @Transactional(readOnly = true)
    @Override
    public List<ProductResponse> getProductOrWishList(Long userId, ProductRequest request) {
        List<Product> results = new ArrayList<>();
        if(request.getLiked() == null) { // 전체 상품 조회
            results = jdbcProductRepository.getProductList(request);
        } else if(request.getLiked()) { // 찜한 상품만 조회
            results = jdbcProductRepository.getWishList(userId, request);
        } else if(!request.getLiked()){
            results = jdbcProductRepository.getNotWishProductList(userId, request);
        }

        return this.toResponse(userId, results);
    }

    private List<ProductResponse> toResponse(Long userId, List<Product> result) {
        List<ProductResponse> responses = new ArrayList<>();
        for (Product product : result) {
            Optional<Wish> userWish = jdbcWishRepository.findById(userId, product.getId());
            if(userWish.isPresent()) {
                responses.add(toProductResponse(product, true));
            } else {
                responses.add(toProductResponse(product, false));
            }
        }

        return responses;
    }


}
