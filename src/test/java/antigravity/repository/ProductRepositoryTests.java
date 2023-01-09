package antigravity.repository;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import antigravity.entity.Product;

@SpringBootTest
@Transactional
public class ProductRepositoryTests {

	@Autowired
	private ProductRepository productRepository;

	@Test
	public void findByIdTest() {
		Long id = 1L;
		Optional<Product> optionalProduct = productRepository.findById(id);
		Assertions.assertTrue(optionalProduct.isPresent());
	}

}
