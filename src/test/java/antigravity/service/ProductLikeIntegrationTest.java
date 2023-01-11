package antigravity.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import antigravity.application.ProductService;
import antigravity.application.exception.AlreadyLikedException;
import antigravity.application.exception.ProductNotFoundException;
import antigravity.domain.member.Member;
import antigravity.domain.member.MemberRepository;
import antigravity.domain.product.Product;
import antigravity.domain.product.ProductRepository;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@DisplayName("상품 찜 등록 통합 테스트")
public class ProductLikeIntegrationTest extends IntegrationTest{

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Transactional
    @Test
    @DisplayName("정상적인 요청이라면 유저의 찜 정보를 등록한다.")
    void like_success() {

        long memberId = 3L;
        long productId = 10L;

        productService.likeProduct(memberId, productId);

        flushAndClearPersistenceContext();

        Product product = productRepository.findById(productId).get();
        Member member = memberRepository.findById(memberId).get();

        assertThat(member.likes(productId)).isTrue();
        assertThat(product.getViewCount()).isOne();
    }

    @Transactional
    @Test
    @DisplayName("이미 찜 등록을 한 상품이라면 AlreadyLiked 예외를 발생시킨다.")
    void already_liked_product() {
        long memberId = 3L;
        long productId = 10L;

        productService.likeProduct(memberId, productId);

        flushAndClearPersistenceContext();

        assertThatThrownBy(() -> productService.likeProduct(memberId, productId))
            .isInstanceOf(AlreadyLikedException.class);
    }

    @Transactional
    @Test
    @DisplayName("존재하지 않는 상품이라면 ProductNotFound 예외를 발생시킨다.")
    void product_not_found() {
        long memberId = 3L;
        long productId = 30L;

        assertThatThrownBy(() -> productService.likeProduct(memberId, productId))
            .isInstanceOf(ProductNotFoundException.class);
    }

    private void flushAndClearPersistenceContext() {
        entityManager.flush();
        entityManager.clear();
    }

}
