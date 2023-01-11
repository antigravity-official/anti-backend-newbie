package antigravity.application;

import antigravity.application.exception.AlreadyLikedException;
import antigravity.application.exception.ProductNotFoundException;
import antigravity.application.exception.UserNotFoundException;
import antigravity.domain.member.Member;
import antigravity.domain.member.MemberRepository;
import antigravity.domain.product.Product;
import antigravity.domain.product.ProductRepository;
import antigravity.domain.product.LikeStatus;
import antigravity.web.response.PagingInfo;
import antigravity.web.response.ProductListResponse;
import antigravity.web.response.ProductResponse;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ProductService {

    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;

    @Transactional
    public void likeProduct(Long memberId, Long productId) {
        Member member = memberRepository.findById(memberId).orElseThrow(UserNotFoundException::new);
        Product product = productRepository.findById(productId)
            .orElseThrow(ProductNotFoundException::new);

        if (member.likes(productId)) {
            throw new AlreadyLikedException();
        }

        member.like(product);
    }

    @Transactional(readOnly = true)
    public ProductListResponse searchProducts(Long memberId, LikeStatus likeStatus, Pageable pageable) {

        Member member = findMember(memberId);
        Page<Product> products = productRepository.findProductsByStatus(memberId, likeStatus,
            pageable);

        List<ProductResponse> productResponses = convertToDto(products.getContent(), member,
            likeStatus);
        PagingInfo pagingInfo = new PagingInfo(products.getTotalElements(),
            products.getTotalPages(), products.hasNext(), products.isLast());

        return new ProductListResponse(productResponses, pagingInfo);
    }

    private Member findMember(Long userId) {
        return memberRepository.findById(userId).orElseThrow(UserNotFoundException::new);
    }

    private List<ProductResponse> convertToDto(List<Product> products, Member member, LikeStatus likeStatus) {

        if (!LikeStatus.NONE.equals(likeStatus)) {
            boolean status = Boolean.parseBoolean(likeStatus.name());
            return products.stream()
                .map((product) -> new ProductResponse(product, status))
                .collect(Collectors.toList());
        }

        return products.stream()
            .map((product) -> new ProductResponse(product, member.likes(product.getId())))
            .collect(Collectors.toList());
    }

}
