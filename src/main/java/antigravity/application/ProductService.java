package antigravity.application;

import antigravity.application.exception.AlreadyLikedException;
import antigravity.application.exception.ProductNotFoundException;
import antigravity.application.exception.UserNotFoundException;
import antigravity.domain.product.Product;
import antigravity.domain.product.ProductRepository;
import antigravity.domain.user.Member;
import antigravity.domain.user.MemberRepository;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ProductService {

    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;

    @Transactional
    public void likeProduct(Long userId, Long productId) {
        Member member = memberRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        Product product = productRepository.findById(productId)
            .orElseThrow(ProductNotFoundException::new);

        if (member.likes(productId)) {
            throw new AlreadyLikedException();
        }

        member.like(product);
    }
}
