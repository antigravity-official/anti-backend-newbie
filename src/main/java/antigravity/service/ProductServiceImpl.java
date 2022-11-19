package antigravity.service;

import antigravity.entity.Product;
import antigravity.entity.Wish;
import antigravity.global.exception.BusinessException;
import antigravity.global.common.ErrorCode;
import antigravity.global.exception.NotFoundException;
import antigravity.payload.ProductRequest;
import antigravity.payload.ProductResponse;
import antigravity.repository.ProductRepository;
import antigravity.repository.WishRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static antigravity.payload.ProductResponse.toProductResponse;

@Slf4j
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

        wishRepository.save(userId, productId);
        productRepository.updateViewCount(product);
    }

    @Transactional(readOnly = true)
    @Override
    public List<ProductResponse> getProductOrWishList(Long userId, ProductRequest request) {
        List<Product> results = new ArrayList<>();
        if(request.getLiked() == null) { // 전체 상품 조회
            results = productRepository.getProductList(request);
        } else if(request.getLiked()) { // 찜한 상품만 조회
            results = productRepository.getWishList(userId, request);
        } else if(!request.getLiked()){
            results = productRepository.getNotWishProductList(userId, request);
        }

        return this.toResponse(userId, results);
    }

    private List<ProductResponse> toResponse(Long userId, List<Product> result) {
        List<ProductResponse> responses = new ArrayList<>();
        for (Product product : result) {
            Optional<Wish> userWish = wishRepository.findById(userId, product.getId());
            if(userWish.isPresent()) {
                responses.add(toProductResponse(product, true));
            } else {
                responses.add(toProductResponse(product, false));
            }
        }

        return responses;
    }


}
