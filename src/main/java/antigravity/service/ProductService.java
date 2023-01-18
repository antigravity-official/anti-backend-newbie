package antigravity.service;

import antigravity.constant.ErrorCode;
import antigravity.exception.GeneralException;
import antigravity.payload.ProductResponse;
import antigravity.repository.BasketRepository;
import antigravity.repository.ProductInfoRepository;
import antigravity.repository.ProductRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;


@Service
@AllArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductInfoRepository productInfoRepository;
    private final BasketRepository basketRepository;

    @Transactional
    public Boolean insertProductInBasket(Long productId) {

//        User user = userRepository.findByApiId(apiId)
//                .orElseThrow(() ->
//                        new EntityNotFoundException("User not found"));
        return true;


    }

}
