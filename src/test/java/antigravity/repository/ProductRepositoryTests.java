package antigravity.repository;
//
//import antigravity.domain.entity.Product;
//import antigravity.domain.repository.ProductRepository;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.transaction.annotation.Transactional;

import antigravity.DoSomething;
import antigravity.domain.entity.Product;
import antigravity.domain.entity.User;
import antigravity.domain.entity.Want;
import antigravity.domain.repository.ProductRepository;
import antigravity.domain.repository.UserRepository;
import antigravity.exception.ErrorMessage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;

import javax.transaction.Transactional;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.concurrent.RejectedExecutionException;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
public class ProductRepositoryTests {

    @Autowired
    private ProductRepository productRepository;

    @DisplayName("정확한 상품이 없을 시 empty반환한다.")
    @Test
    public void findByIdTest() {
        Long id = 1L;
        Optional<Product> product = productRepository.findById(id);
        Assertions.assertNotNull(product);
    }

    @DisplayName("찾는 상품이 없을 때 예외를 발생시킨다.")
    @Test
    public void returnProductError() {
        Long id = 100L;
        Assertions.assertThrows(NoSuchElementException.class, () -> {
            productRepository.findById(id).get();
        });
    }


}
