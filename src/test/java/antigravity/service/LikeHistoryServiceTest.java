package antigravity.service;

import antigravity.common.BaseException;
import antigravity.entity.Member;
import antigravity.entity.Product;
import antigravity.repository.LikeHistoryRepository;
import antigravity.repository.MemberRepository;
import antigravity.repository.ProductRepository;
import java.math.BigDecimal;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.repository.CrudRepository;


@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class LikeHistoryServiceTest {
    @Autowired
    LikeHistoryRepository likeHistoryRepository;
    @Autowired
    List<? extends CrudRepository> crudRepositories;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    LikeHistoryService likeHistoryService;

    Member alreadySaveMember;

    Product alreadySaveProduct;

    @BeforeEach
    public void setup() {
        likeHistoryRepository.deleteAll();
        crudRepositories.stream().forEach(CrudRepository::deleteAll);
        Member member = Member.builder().email("test").name("test").build();
        alreadySaveMember = memberRepository.save(member);
        Product product = Product.builder().sku("test").price(BigDecimal.ONE).name("testprodcut").quantity(10).build();
        alreadySaveProduct = productRepository.save(product);
    }

    @Test
    public void alreadySaveMemberDifferentProductTest() {
        Product product = Product.builder().sku("test").price(BigDecimal.TEN).name("testprodcut").quantity(10).build();
        product = productRepository.save(product);
        likeHistoryService.addNotDuplicatedLikeHistory(product.getId(), alreadySaveMember.getId());
        likeHistoryService.addNotDuplicatedLikeHistory(alreadySaveProduct.getId(), alreadySaveMember.getId());
        int expect = 2;
        Assertions.assertThat(expect).isEqualTo(likeHistoryRepository.findAll().size());
    }

    @Test
    public void alreadySaveProductDifferentMemberTest() {
        Member member = Member.builder().email("test").name("test1").build();
        member = memberRepository.save(member);
        likeHistoryService.addNotDuplicatedLikeHistory(alreadySaveProduct.getId(), alreadySaveMember.getId());
        likeHistoryService.addNotDuplicatedLikeHistory(alreadySaveProduct.getId(), member.getId());
        int expect = 2;
        Assertions.assertThat(expect).isEqualTo(likeHistoryRepository.findAll().size());
    }

    @Test
    public void errorAddTest() {
        likeHistoryService.addNotDuplicatedLikeHistory(alreadySaveProduct.getId(), alreadySaveMember.getId());

        Assertions.assertThatThrownBy(() -> likeHistoryService.addNotDuplicatedLikeHistory(alreadySaveProduct.getId(),
                        alreadySaveMember.getId()))
                .isInstanceOf(BaseException.class);
    }
}