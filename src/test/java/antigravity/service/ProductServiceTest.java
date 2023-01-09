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
import antigravity.repository.ProductRepository;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

	@Mock
	private ProductRepository productRepository;

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
		IllegalStateException e = assertThrows(IllegalStateException.class,
			() -> productService.findProductById(productId));
		assertEquals("Product Not Found", e.getMessage());
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
		IllegalStateException e = assertThrows(IllegalStateException.class,
			() -> productService.findProductById(productId));
		assertEquals("Already Product Deleted", e.getMessage());
	}
}