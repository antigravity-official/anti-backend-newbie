package antigravity.service;

import static org.mockito.ArgumentMatchers.any;

import java.math.BigDecimal;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import antigravity.entity.Product;
import antigravity.entity.User;
import antigravity.exception.custom.BusinessException;
import antigravity.repository.CartRepository;
import antigravity.repository.ProductRepository;
import antigravity.repository.UserRepository;
import antigravity.service.mapper.CartMapper;

@ExtendWith(MockitoExtension.class)
public class CartServiceMockTest {

	@InjectMocks
	CartService cartService;

	@Mock
	CartMapper cartMapper;
	@Mock
	ProductCountingService productCountingService;
	@Mock
	UserRepository userRepository;
	@Mock
	ProductRepository productRepository;
	@Mock
	CartRepository cartRepository;

	@Test
	@DisplayName("찜을 중복하면 예외가 발생한다")
	void testDuplicateLike() {
		//given
		User user = User.builder()
			.email("daidn@antigravity.com")
			.name("user1")
			.build();

		Product product = Product.builder()
			.sku("G2000000011")
			.name("소울 라이트 브라")
			.price(new BigDecimal("34900.00"))
			.quantity(10)
			.build();

		BDDMockito.given(userRepository.findByIdAndDeletedAtIsNull(user.getId())).willReturn(Optional.of(user));
		BDDMockito.given(productRepository.findByIdAndDeletedAtIsNull(product.getId())).willReturn(Optional.of(product));
		BDDMockito.given(cartRepository.save(any())).willThrow(DataIntegrityViolationException.class);

		//when
		//then

		Assertions.assertThatThrownBy(()->{
			cartService.like(user.getId(),product.getId());
		}).isInstanceOf(BusinessException.class);

	}
}
