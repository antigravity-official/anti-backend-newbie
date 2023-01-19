package antigravity.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.utility.DockerImageName;

import antigravity.common.dto.PageResponseDto;
import antigravity.controller.dto.CartResponses;
import antigravity.controller.dto.LikeResponse;
import antigravity.controller.dto.LikeSearchDto;
import antigravity.entity.Product;
import antigravity.entity.User;
import antigravity.repository.ProductRepository;
import antigravity.repository.UserRepository;

@SpringBootTest
class CartServiceTest {
	@Autowired
	UserRepository userRepository;

	@Autowired
	ProductRepository productRepository;

	@Autowired
	CartService cartService;

	@Autowired
	ProductCountingService productCountingService;

	@Container
	static final GenericContainer<?> redis = new GenericContainer<>(
		DockerImageName.parse("redis:latest")).withExposedPorts(6379);

	static {
		redis.start();
		System.setProperty("spring.redis.host", redis.getHost());
		System.setProperty("spring.redis.port", redis.getMappedPort(6379).toString());
	}

	@DisplayName("상품을 찜하면 조회수와 좋아요가 증가한다.")
	@Test
	void testLike() {
		// given

		User user1 = userRepository.save(User.builder()
			.email("daidn@antigravity.com")
			.name("user1")
			.build());

		Product product = productRepository.save(Product.builder()
			.sku("G2000000011")
			.name("소울 라이트 브라")
			.price(new BigDecimal("34900.00"))
			.quantity(10)
			.build()
		);

		// when
		LikeResponse likeResponse = cartService.like(user1.getId(), product.getId());

		// then
		assertThat(likeResponse.getName()).isEqualTo(product.getName());
		assertThat(likeResponse.getSku()).isEqualTo(product.getSku());
		assertThat(likeResponse.getPrice()).isEqualTo(product.getPrice());
		assertThat(likeResponse.getUserId()).isEqualTo(user1.getId());
	}

	@Test
	@DisplayName("찜을 누른 상품 목록들을 조회한다.")
	void testSearch() {
		//given
		User user1 = userRepository.save(User.builder()
			.email("daidn@antigravity.com")
			.name("user1")
			.build());

		Product product1 = productRepository.save(Product.builder()
			.sku("G2000000011")
			.name("소울 라이트 브라")
			.price(new BigDecimal(34900))
			.quantity(10)
			.build());

		Product product2 = productRepository.save(Product.builder()
			.sku("G2000000010")
			.name("더핏브라")
			.price(new BigDecimal("34900.00"))
			.quantity(10)
			.build());

		cartService.like(user1.getId(), product1.getId());
		cartService.like(user1.getId(), product2.getId());

		LikeSearchDto searchDto = new LikeSearchDto(1, 10, true);

		//when
		PageResponseDto<CartResponses> search = cartService.search(user1.getId(), searchDto);
		List<CartResponses> contents = search.getContents();

		//then
		assertThat(search.getPage()).isEqualTo(searchDto.getPage());
		assertThat(search.getSize()).isEqualTo(searchDto.getSize());
		assertThat(search.getContents().size()).isEqualTo(2);
		contents.forEach(cartResponses -> {
			assertThat(cartResponses.getTotalLiked()).isEqualTo(1);
			assertThat(cartResponses.getViewed()).isEqualTo(1);
		});
	}

	@Test
	@DisplayName("찜 동시성 테스트")
	void testLikeWithConcurrency() throws InterruptedException {
		//given

		List<User> users = IntStream.rangeClosed(1, 100)
			.mapToObj(value -> User.builder()
				.email("user" + value + "@antigravity.com")
				.name("user" + value)
				.build()
			).collect(Collectors.toList());

		userRepository.saveAll(users);

		System.out.println("@@@@@@@@@@@@@@"+users.size());

		Product product1 = productRepository.save(Product.builder()
			.sku("G2000000011")
			.name("소울 라이트 브라")
			.price(new BigDecimal(34900))
			.quantity(10)
			.build());

		int requestSize = 100;

		ExecutorService executorService = Executors.newFixedThreadPool(users.size());
		CountDownLatch countDownLatch = new CountDownLatch(users.size());

		//when
		users.stream().map(User::getId)
			.forEach(userId -> {
				executorService.submit(() -> {
					try {
						cartService.like(userId, product1.getId());
					} finally {
						countDownLatch.countDown();
					}
				});
			});

		countDownLatch.await();

		Long productLikeCount = productCountingService.findProductLikeCount(product1.getId());
		Long productViewCount = productCountingService.findProductLikeCount(product1.getId());

		assertThat(productLikeCount).isEqualTo(requestSize);
		assertThat(productViewCount).isEqualTo(requestSize);
	}

}