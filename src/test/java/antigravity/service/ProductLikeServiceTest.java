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
import antigravity.exception.AntigravityException;
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
		Long userId = 1L;

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
		assertDoesNotThrow(() -> productLikeService.saveProductLike(productId, userId));
	}

	@DisplayName("찜 상품 등록 테스트 - 유요하지 않은 productId 일때")
	@Test
	void given_InvalidProductId_when_ProductLike_then_ThrowsIllegalStateException() {
		//given
		Long productId = 1L;
		Long userId = 1L;

		//when
		when(productService.findProductById(productId)).thenThrow(new IllegalStateException());

		//then
		assertThrows(IllegalStateException.class,
			() -> productLikeService.saveProductLike(productId, userId));
	}

	@DisplayName("찜 상품 등록 테스트 - 유요하지 않은 userId 일때")
	@Test
	void given_InvalidUserId_when_ProductLike_then_ThrowsIllegalStateException() {
		//given
		Long productId = 1L;
		Long userId = 1L;

		//mock
		Product mockedProduct = mock(Product.class);

		//when
		when(productService.findProductById(productId)).thenReturn(mockedProduct);
		when(userService.findUserById(userId)).thenThrow(new IllegalStateException());

		//then
		assertThrows(IllegalStateException.class,
			() -> productLikeService.saveProductLike(productId, userId));
	}

	@DisplayName("찜 상품 등록 테스트 - 이미 찜한 상품일 때")
	@Test
	void given_AlreadyProductLike_when_ProductLike_then_ThrowsIllegalStateException() {
		//given
		Long productId = 1L;
		Long userId = 1L;

		//mock
		Product mockedProduct = mock(Product.class);
		User mockedUser = mock(User.class);
		ProductLike mockedProductLike = mock(ProductLike.class);

		//when
		when(productService.findProductById(productId)).thenReturn(mockedProduct);
		when(userService.findUserById(userId)).thenReturn(mockedUser);
		when(productLikeRepository.findByProductAndUser(mockedProduct, mockedUser)).thenReturn(
			Optional.of(mockedProductLike));

		//then
		AntigravityException e = assertThrows(AntigravityException.class,
			() -> productLikeService.saveProductLike(productId, userId));
		assertEquals(String.format("Already Liked Product. userId=%d is already liked productId=%d",userId,productId)
			, e.getMessage());
	}

}
