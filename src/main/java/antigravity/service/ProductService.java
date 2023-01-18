package antigravity.service;

import antigravity.constant.ErrorCode;
import antigravity.exception.GeneralException;
import antigravity.payload.ProductResponse;
import antigravity.repository.ProductRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;


@Service
@AllArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public boolean putInBasket(Long productId) {
        try {
            return productRepository.insertProduct(productId);
        }
        catch (Exception e) {
            throw new GeneralException(ErrorCode.DATA_ACCESS_ERROR, e);
        }
    }

}
