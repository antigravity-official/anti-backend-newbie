package antigravity.service;

import antigravity.entity.ProductHits;
import antigravity.payload.ProductResponse;
import antigravity.payload.ProductSearchCriteria;
import antigravity.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductHitsService productHitsService;

    @Transactional(readOnly = true)
    public List<ProductResponse> findProducts(Long userId, ProductSearchCriteria productSearchCriteria) {
        productSearchCriteria.setUserId(userId);
        List<ProductResponse> productAndWishList = productRepository.findProductAndWishList(productSearchCriteria);
        Long[] targetProductIds = filterTargetProductId(productAndWishList);
        List<ProductHits> productHit = productHitsService.getProductHit(targetProductIds);
        mergeToProduct(productHit, productAndWishList);
        return productAndWishList;
    }

    private Long[] filterTargetProductId(List<ProductResponse> productAndWishList) {
        if( CollectionUtils.isEmpty(productAndWishList) ) {
            return null;
        }
        return productAndWishList.stream().map(ProductResponse::getId).toArray(Long[]::new);
    }

    private void mergeToProduct(List<ProductHits> from, List<ProductResponse> to) {
        for (ProductResponse t : to) {
            for (ProductHits f : from) {
                if( t.getId().equals(f.getId()) ) {
                    t.setViewed(Math.toIntExact(f.getHit()));
                }
            }
        }
    }
}
