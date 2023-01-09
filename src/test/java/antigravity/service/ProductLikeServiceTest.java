package antigravity.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import antigravity.entity.Product;
import antigravity.entity.ProductLike;
import antigravity.entity.User;
import antigravity.repository.ProductLikeRepository;

@ExtendWith(MockitoExtension.class)
public class ProductLikeServiceTest {

	@Mock
	private ProductLikeRepository productLikeRepository;

	@Mock
	private UserService userService;

	@Mock
	private ProductService productService;

	@InjectMocks
	private ProductLikeService productLikeService;

	@DisplayName("찜 상품 등록 테스트")
	@Test
	void given_ProductIdAndUserId_when_ProductLike_then_DoesNotThrow() {
		//given
		Long productId = 1L;
		Integer userId = 1;

		//mock
		Product mockedProduct = mock(Product.class);
		User mockedUser = mock(User.class);
		ProductLike mockedProductLike = mock(ProductLike.class);

		//when
		when(productService.findProductById(productId)).thenReturn(mockedProduct);
		when(userService.findUserById(userId)).thenReturn(mockedUser);
		when(productLikeRepository.findByProductAndUser(mockedProduct, mockedUser)).thenReturn(Optional.empty());
		when(productLikeRepository.save(any(ProductLike.class))).thenReturn(mockedProductLike);
		doNothing().when(mockedProduct).increaseViewed();

		//then
		assertDoesNotThrow(() -> productLikeService.productLike(productId, userId));
	}

}
