package antigravity.service;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import antigravity.entity.LikedProduct;
import antigravity.entity.Product;
import antigravity.entity.User;
import antigravity.exception.custom.AlreadyLikedException;
import antigravity.exception.custom.ProductNotFoundException;
import antigravity.exception.custom.UserNotFoundException;
import antigravity.payload.ProductResponse;
import antigravity.payload.ProductSearch;
import antigravity.repository.LikedProductRepository;
import antigravity.repository.ProductRepository;
import antigravity.repository.UserRepository;

@SpringBootTest
@Transactional
class ProductServiceTest {

	@Autowired
	private ProductService ProductService;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private LikedProductRepository likedProductRepository;

	private User user;
	private Product product;

	@BeforeEach
	void setup() {
		user = new User("abc@naver.com", "user1");
		product = Product.builder()
				.sku("G9000000019")
				.name("No1. 더핏세트")
				.price(BigDecimal.valueOf(42800))
				.quantity(10)
				.views(0)
				.build();

		userRepository.save(user);
		productRepository.save(product);
	}

	@Test
	@DisplayName("찜하기 성공 테스트")
	void test1() {
		// given when
		ProductService.registerLikedProduct(product.getId(), user.getId());

		// then
		LikedProduct likedProduct = likedProductRepository
				.findByUserAndProduct(user, product)
				.orElseThrow(EntityNotFoundException::new);
		assertAll(
				() -> assertThat(likedProduct.getId()).isEqualTo(1L),
				() -> assertThat(likedProduct.getUser().getId()).isEqualTo(1L),
				() -> assertThat(likedProduct.getProduct().getId()).isEqualTo(1L),
				() -> assertThat(likedProduct.getUser().getLikedProducts().size()).isEqualTo(1),
				() -> assertThat(likedProduct.getProduct().getViews()).isEqualTo(1)
		);
	}

	@Test
	@DisplayName("찜하기 실패 테스트 : 유저 정보 없음")
	void test2() {
		// given
		Long NotSavedUserId = 100L;

		// expected
		assertThatThrownBy(
				() -> ProductService.registerLikedProduct(product.getId(), NotSavedUserId))
				.isInstanceOf(UserNotFoundException.class)
				.hasMessageContaining("존재하지 않는 유저입니다.");
	}

	@Test
	@DisplayName("찜하기 실패 테스트 : 제품 정보 없음")
	void test3() {
		// given
		Long NotSavedProductId = 100L;

		// expected
		assertThatThrownBy(
				() -> ProductService.registerLikedProduct(NotSavedProductId, user.getId()))
				.isInstanceOf(ProductNotFoundException.class)
				.hasMessageContaining("존재하지 않는 상품입니다.");
	}

	@Test
	@DisplayName("찜하기 실패 테스트 : 이미 찜한 상품")
	void test4() {
		// given
		LikedProduct likedProduct = new LikedProduct(user, product);
		likedProductRepository.save(likedProduct);

		// expected
		assertThatThrownBy(
				() -> ProductService.registerLikedProduct(product.getId(), user.getId()))
				.isInstanceOf(AlreadyLikedException.class)
				.hasMessageContaining("이미 찜한 상품입니다.");
	}

	@Test
	@DisplayName("상품 조회하기 - like=true")
	void test5() {
		// given
		LikedProduct likedProduct = new LikedProduct(user, product);
		likedProductRepository.save(likedProduct);
		ProductSearch productSearch = ProductSearch.builder()
				.liked(true)
				.page(1).size(10)
				.build();

		// when
		ResponseEntity<List<ProductResponse>> products = ProductService.findProducts(user.getId(), productSearch);

		// then
		assertThat(products.getBody().size()).isEqualTo(1);
	}

	@Test
	@DisplayName("상품 조회하기 - like=false")
	void test6() {
		//given
		LikedProduct likedProduct = new LikedProduct(user, product);
		likedProductRepository.save(likedProduct);
		ProductSearch productSearch = ProductSearch.builder()
				.liked(false)
				.page(1).size(10)
				.build();
		// when
		ResponseEntity<List<ProductResponse>> products = ProductService.findProducts(user.getId(), productSearch);

		// then
		assertThat(products.getBody().size()).isEqualTo(0);
	}
}
