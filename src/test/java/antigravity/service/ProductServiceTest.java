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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import antigravity.entity.Product;
import antigravity.entity.User;
import antigravity.exception.AntigravityException;
import antigravity.repository.ProductRepository;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

	@Mock
	private ProductRepository productRepository;

	@Mock
	private UserService userService;

	@InjectMocks
	private ProductService productService;

	@DisplayName("상품 찾기 테스트")
	@Test
	void given_ProductId_when_FindProduct_then_DoesNotThrow() {
		//given
		Long productId = 1L;

		//mock
		Product mockedProduct = mock(Product.class);

		//when
		when(productRepository.findById(productId)).thenReturn(Optional.of(mockedProduct));
		when(mockedProduct.isDeleted()).thenReturn(false);

		//then
		assertDoesNotThrow(() -> productService.findProductById(productId));
	}

	@DisplayName("상품 찾기 테스트 - productId로 조회한 상품이 존재하지 않을 때")
	@Test
	void given_NonExistentProductId_when_FindProduct_then_DoesThrowIllegalStateException() {
		//given
		Long productId = 1L;

		//when
		when(productRepository.findById(productId)).thenReturn(Optional.empty());

		//then
		AntigravityException e = assertThrows(AntigravityException.class,
			() -> productService.findProductById(productId));
		assertEquals(String.format("Product ID is not founded. %d is not founded", productId), e.getMessage());
	}

	@DisplayName("상품 찾기 테스트")
	@Test
	void given_DeletedProductId_when_FindProduct_then_DoesThrowIllegalStateException() {
		//given
		Long productId = 1L;

		//mock
		Product mockedProduct = mock(Product.class);

		//when
		when(productRepository.findById(productId)).thenReturn(Optional.of(mockedProduct));
		when(mockedProduct.isDeleted()).thenReturn(true);

		//then
		AntigravityException e = assertThrows(AntigravityException.class,
			() -> productService.findProductById(productId));
		assertEquals(String.format("Already Deleted Product. %d already deleted", productId), e.getMessage());
	}

	@DisplayName("상품 전체 조회 테스트")
	@Test
	void given_UserIdAndPageable_when_GetAllProducts_then_DoesNotThrow() {
		//given
		Long userId = 1L;
		Pageable pageable = mock(Pageable.class);
		Boolean liked = null;

		//mock
		User mockedUser = mock(User.class);
		Page<Product> mockedPage = mock(Page.class);

		//when
		when(userService.findUserById(userId)).thenReturn(mockedUser);
		when(productRepository.findAll(pageable)).thenReturn(mockedPage);
		when(mockedPage.isEmpty()).thenReturn(false);

		//then
		assertDoesNotThrow(() -> productService.getProducts(userId, pageable, liked));
	}

	@DisplayName("상품 전체 조회 테스트 - 상품이 존재하지 않을 때")
	@Test
	void given_NotExistProduct_when_GetAllProducts_then_ThrowIllegalStateException() {
		//given
		Long userId = 1L;
		Pageable pageable = mock(Pageable.class);
		Boolean liked = null;

		//mock
		User mockedUser = mock(User.class);
		Page<Product> mockedPage = mock(Page.class);

		//when
		when(userService.findUserById(userId)).thenReturn(mockedUser);
		when(productRepository.findAll(pageable)).thenReturn(mockedPage);
		when(mockedPage.isEmpty()).thenReturn(true);

		//then
		AntigravityException e = assertThrows(AntigravityException.class,
			() -> productService.getProducts(userId, pageable, liked));
		assertEquals(String.format("Nonexistent Products. userId=%d, pageable=%s, liked=%s", userId, pageable, liked),
			e.getMessage());
	}

	@DisplayName("찜하지 않은 상품 조회 테스트")
	@Test
	void given_UserIdAndPageable_when_GetUnlikedProducts_then_DoesNotThrow() {
		//given
		Long userId = 1L;
		Pageable pageable = mock(Pageable.class);
		Boolean liked = false;

		//mock
		User mockedUser = mock(User.class);
		Page<Product> mockedPage = mock(Page.class);

		//when
		when(userService.findUserById(userId)).thenReturn(mockedUser);
		when(productRepository.findAllUnlikeProduct(mockedUser, pageable)).thenReturn(mockedPage);
		when(mockedPage.isEmpty()).thenReturn(false);

		//then
		assertDoesNotThrow(() -> productService.getProducts(userId, pageable, liked));
	}

	@DisplayName("찜하지 않은 상품 조회 테스트 - 상품이 존재하지 않을 때")
	@Test
	void given_NotExistProduct_when_GetUnlikedProducts_then_ThrowIllegalStateException() {
		//given
		Long userId = 1L;
		Pageable pageable = mock(Pageable.class);
		Boolean liked = false;

		//mock
		User mockedUser = mock(User.class);
		Page<Product> mockedPage = mock(Page.class);

		//when
		when(userService.findUserById(userId)).thenReturn(mockedUser);
		when(productRepository.findAllUnlikeProduct(mockedUser, pageable)).thenReturn(mockedPage);
		when(mockedPage.isEmpty()).thenReturn(true);

		//then
		AntigravityException e = assertThrows(AntigravityException.class,
			() -> productService.getProducts(userId, pageable, liked));
		assertEquals(String.format("Nonexistent Products. userId=%d, pageable=%s, liked=%s", userId, pageable, liked),
			e.getMessage());
	}

	@DisplayName("찜한 상품 조회 테스트")
	@Test
	void given_UserIdAndPageable_when_GetLikedProducts_then_DoesNotThrow() {
		//given
		Long userId = 1L;
		Pageable pageable = mock(Pageable.class);
		Boolean liked = true;

		//mock
		User mockedUser = mock(User.class);
		Page<Product> mockedPage = mock(Page.class);

		//when
		when(userService.findUserById(userId)).thenReturn(mockedUser);
		when(productRepository.findAllLikeProduct(mockedUser, pageable)).thenReturn(mockedPage);
		when(mockedPage.isEmpty()).thenReturn(false);

		//then
		assertDoesNotThrow(() -> productService.getProducts(userId, pageable, liked));
	}

	@DisplayName("찜한 상품 조회 테스트 - 상품이 존재하지 않을 때")
	@Test
	void given_NotExistProduct_when_GetLikedProducts_then_ThrowIllegalStateException() {
		//given
		Long userId = 1L;
		Pageable pageable = mock(Pageable.class);
		Boolean liked = true;

		//mock
		User mockedUser = mock(User.class);
		Page<Product> mockedPage = mock(Page.class);

		//when
		when(userService.findUserById(userId)).thenReturn(mockedUser);
		when(productRepository.findAllLikeProduct(mockedUser, pageable)).thenReturn(mockedPage);
		when(mockedPage.isEmpty()).thenReturn(true);

		//then
		AntigravityException e = assertThrows(AntigravityException.class,
			() -> productService.getProducts(userId, pageable, liked));
		assertEquals(String.format("Nonexistent Products. userId=%d, pageable=%s, liked=%s", userId, pageable, liked),
			e.getMessage());
	}

}