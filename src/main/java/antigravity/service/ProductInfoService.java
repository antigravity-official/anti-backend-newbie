package antigravity.service;

import antigravity.constant.ErrorCode;
import antigravity.entity.Basket;
import antigravity.entity.ProductInfo;
import antigravity.exception.GeneralException;
import antigravity.repository.ProductInfoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class ProductInfoService {

    private final ProductInfoRepository productInfoRepository;

    @Transactional
    public Boolean changeViewProduct(Long productId) {
        try {
            ProductInfo productInfo = productInfoRepository.findById(productId).get();
            ProductInfo productInfo2 = ProductInfo.changeViewProduct(productInfo.getTotalLiked() + 1,
                    productInfo.getViewed() + 1
                    );

            productInfoRepository.save(productInfo2);
            return true;
        }
        catch (Exception e) {
            throw new GeneralException(ErrorCode.DATA_ACCESS_ERROR, e);
        }
    }
}
