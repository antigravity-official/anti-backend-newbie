package antigravity.service;

import antigravity.constant.ErrorCode;
import antigravity.entity.Basket;
import antigravity.entity.ProductInfo;
import antigravity.exception.GeneralException;
import antigravity.repository.ProductInfoRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@AllArgsConstructor
public class ProductInfoService {

    private final ProductInfoRepository productInfoRepository;

    @Transactional
    public Boolean changeViewProduct(Long productId) {
        try {
            final Optional<ProductInfo> productInfo = productInfoRepository.findById(productId);
            productInfo.ifPresent(productInfo1 -> {
                productInfo1.setTotalLiked(productInfo1.getTotalLiked() + 1);
                productInfo1.setViewed(productInfo1.getViewed() + 1);
                productInfoRepository.save(productInfo1);
            });

            return true;
        }
        catch (Exception e) {
            throw new GeneralException(ErrorCode.DATA_ACCESS_ERROR, e);
        }
    }
}
