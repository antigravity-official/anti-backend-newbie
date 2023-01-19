package antigravity.service;

import antigravity.constant.ErrorCode;
import antigravity.entity.Basket;
import antigravity.exception.GeneralException;
import antigravity.repository.BasketRepository;
import antigravity.repository.ProductInfoRepository;
import antigravity.repository.ProductRepository;
import antigravity.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@AllArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductInfoRepository productInfoRepository;
    private final BasketRepository basketRepository;
    private final UserRepository userRepository;


    @Transactional
    public Boolean insertProductInBasket(Long productId, Integer userId) {

        if (basketRepository.existsBasketByProductIdAndUserId(productId, userId.longValue()))
            throw new GeneralException(ErrorCode.BAD_REQUEST);
        else if (productRepository.existsById(productId) == false)
            throw new GeneralException(ErrorCode.NOT_FOUND);

        // TODO : user 고정 나중에 수정하기
        try {
            Basket basket = Basket.choiceProduct(true,
                    productRepository.findById(productId).get(),
                    userRepository.findById(userId.longValue()).get()
                   );
            
            basketRepository.save(basket);
            return true;
        }
        catch (Exception e) {
            throw new GeneralException(ErrorCode.DATA_ACCESS_ERROR, e);
        }

    }
}
