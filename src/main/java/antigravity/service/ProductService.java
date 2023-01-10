package antigravity.service;

import antigravity.entity.Heart;
import antigravity.entity.ViewCount;
import antigravity.payload.ProductResponse;
import antigravity.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repository;

    @Transactional
    public int isAlreadyLiked(Long memberId, Long productId) {
        List<Heart> heart = repository.isAlreadyLiked(memberId, productId);
        if(heart.size() != 0) { // 이미 좋아요한 상품이라면
            return repository.deleteLikedProduct(memberId, productId);
        }
        return likeProduct(memberId, productId);
    }

    public int likeProduct(Long memberId, Long productId) {
        int countUp = checkViewCount(productId);
        if(countUp > 0) {
            return repository.likeProduct(memberId, productId);
        }
        return 0;
    }

    @Transactional
    public int checkViewCount(Long productId) {
        ViewCount view = repository.checkViewCount(productId);
        if(view != null) {
            return repository.updateViewCount(productId);
        }
        return repository.addViewCount(productId);
    }

    public List<ProductResponse> getAllProduct(Long memberId, int listSize, int startList) {
        List<ProductResponse> list = repository.getAllProduct(memberId, listSize, startList);
        for(ProductResponse pr : list) {
            if(pr.getMemberId() != 0) { // 사용자가 찜한 상품이라면
                pr.setLiked(true);
            }
        }
        return list;
    }

    public List<ProductResponse> getLikedProduct(Long memberId, boolean like, int listSize, int startList) {
        if(like)    return repository.getLikedProduct(memberId, listSize, startList);
        else        return repository.getNotLikedProduct(memberId, listSize, startList);
    }
}
