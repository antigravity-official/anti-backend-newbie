package antigravity.service;

import antigravity.entity.Product;
import antigravity.entity.ProductLike;
import antigravity.entity.User;
import antigravity.exception.CustomException;
import antigravity.exception.ErrorCode;
import antigravity.payload.response.SearchResponse;
import antigravity.repository.ProductLikeRepository;
import antigravity.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SearchProductService {
    private final ProductRepository productRepository;
    private final ProductLikeRepository productLikeRepository;

    private final SearchUserService searchUserService;


    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Product searchProductByProductId(Long productId) {
        Product product =  productRepository.findById(productId)
                .orElseThrow(()-> {
                    throw new CustomException(ErrorCode.NOT_FOUND_PRODUCT);
                });

        if (product.getDeletedAt() != null) {
            throw new CustomException(ErrorCode.NOT_EXISTS_PRODUCT);
        }

        productRepository.increaseViewed(productId);
        return product;
    }

    public List<SearchResponse> searchProduct(Long userId, Boolean liked, Pageable pageable) {
        User user = searchUserService.searchUserByUserId(userId);

        if (liked == null) {
            return productAll(user, pageable);
        }

        return liked ? productTureLiked(user, pageable) : productFalseLiked(user, pageable);
    }

    @Transactional(readOnly = true)
    public List<SearchResponse> productAll(User user, Pageable pageable) {
        List<SearchResponse> responseList = new ArrayList<>();
        for (Product product : productRepository.findAllExceptDeletedAt(pageable)) {
            if (product.getTotalLiked() > 0) {
                Boolean isExistsLiked = productLikeRepository.existsByUserAndProduct(user, product);
                responseList.add(new SearchResponse(product, isExistsLiked));
                continue;
            }
            responseList.add(new SearchResponse(product, false));
        }
        return responseList;
    }

    @Transactional(readOnly = true)
    public List<SearchResponse> productTureLiked(User user, Pageable pageable) {
        List<ProductLike> responseList = productLikeRepository.findAllByUserId(user.getId(), pageable);

        return responseList
                .stream()
                .map(productLike -> new SearchResponse(productLike.getProduct(), true))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<SearchResponse> productFalseLiked(User user, Pageable pageable) {
        List<Product> responseList = productRepository.findAllFalseLikedByUserId(user, pageable);

        return responseList
                .stream()
                .map(product -> new SearchResponse(product, false))
                .collect(Collectors.toList());
    }
}