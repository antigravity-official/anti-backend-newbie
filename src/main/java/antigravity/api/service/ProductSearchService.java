package antigravity.api.service;

import antigravity.api.repository.ProductLikeRepository;
import antigravity.api.repository.ProductRepository;
import antigravity.entity.LikeStatus;
import antigravity.entity.Product;
import antigravity.entity.ProductLike;
import antigravity.entity.User;
import antigravity.payload.response.ProductSearchResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductSearchService {

    private final ProductLikeRepository productLikeRepository;
    private final ProductRepository productRepository;

    private final UserSearchService userSearchService;

    public List<ProductSearchResponse> searchLikedProduct(Long userId, Boolean isLiked, Pageable pageable) {

        User findUser = userSearchService.searchUserByUserId(userId);

        if (isLiked == null) {
            return takeProductAll(findUser, pageable);
        }

        return Boolean.TRUE.equals(isLiked) ?
                takeLikedProductWithUserId(findUser, true, pageable) :
                takeNoLikedProductWithUserId(findUser, false, pageable);
    }

    @Transactional(readOnly = true)
    public List<ProductSearchResponse> takeLikedProductWithUserId(User user, boolean isLiked, Pageable pageable) {
        return productRepository.findLikedProductWithUser(user, LikeStatus.LIKE, pageable)
                .stream()
                .map(product -> new ProductSearchResponse(product, isLiked, product.getProductLikes().size()))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ProductSearchResponse> takeNoLikedProductWithUserId(User user, boolean isLiked, Pageable pageable) {
        return productRepository.findProductsNotInProductLike(user, pageable)
                .stream()
                .map(product -> new ProductSearchResponse(product, isLiked, product.getProductLikes().size()))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ProductSearchResponse> takeProductAll(User user, Pageable pageable) {
        List<ProductSearchResponse> responseList = new ArrayList<>();
        for (Product product : productRepository.findAllProduct(pageable)) {
            boolean flag = false;
            for (ProductLike productLike : product.getProductLikes()) {
                if (productLike.getUser().equals(user)) {
                    responseList.add(new ProductSearchResponse(product, true, product.getProductLikes().size()));
                    flag = true;
                    break;
                }
            }
            if (!flag) {
                responseList.add(new ProductSearchResponse(product, false, product.getProductLikes().size()));
            }
        }
        return responseList;
    }
}