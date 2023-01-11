package antigravity.repository;

import antigravity.entity.LikeHistory;
import antigravity.entity.Member;
import antigravity.entity.Product;
import java.math.BigDecimal;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

@SpringBootTest
class ProductRepositoryTest {

    @Autowired
    LikeHistoryRepository likeHistoryRepository;
    @Autowired
    List<? extends CrudRepository> crudRepositories;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    ProductRepository productRepository;
    Member alreadySaveMember;

    Product alreadySaveProduct;

    @BeforeEach
    public void setUp() {
        likeHistoryRepository.deleteAll();
        crudRepositories.stream().forEach(CrudRepository::deleteAll);
        Member member = Member.builder().email("test").name("test").build();
        alreadySaveMember = memberRepository.save(member);
        Product product = Product.builder().sku("test").price(BigDecimal.ONE).name("testprodcut").quantity(10).build();
        alreadySaveProduct = productRepository.save(product);
        likeHistoryRepository.save(LikeHistory.builder().member(alreadySaveMember).product(alreadySaveProduct).build());
        likeHistoryRepository.save(LikeHistory.builder().member(alreadySaveMember).product(alreadySaveProduct).build());

        productRepository.save(
                Product.builder().sku("test").price(BigDecimal.TEN).name("testprodcut1").quantity(10).build());
    }

    @Test
    public void successTest() {
        Pageable page = PageRequest.of(0, 3);
        List<Product> notLikeHistory = productRepository.getNotLikeProduct(alreadySaveMember.getId(), page);
        int expect = 1;
        Assertions.assertThat(expect).isEqualTo(notLikeHistory.size());
    }

}