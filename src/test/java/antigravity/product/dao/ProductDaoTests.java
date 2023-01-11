package antigravity.product.dao;

import antigravity.global.exception.AntiException;
import antigravity.product.domain.dao.ProductDAO;
import antigravity.product.domain.repository.ProductRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

@ExtendWith(SpringExtension.class)
public class ProductDaoTests {
    @InjectMocks
    private ProductDAO productDAO;
    @Mock
    private ProductRepository productRepository;

    @Test
    @DisplayName("상품이 존재하지 않을 경우 400 에러를 발생시킨다.")
    void notExistProduct() {

        //when
        AntiException exception = assertThrows(AntiException.class, () -> {
            productDAO.validateExistProduct(3L);
        });

        //then
        assertEquals("해당 아이디를 가진 상품이 존재하지 않습니다.",exception.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, exception.getHttpStatus());
    }
}
