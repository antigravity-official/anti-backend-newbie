package antigravity.service;

import antigravity.controller.handler.CustomError;
import antigravity.entity.Like;
import antigravity.entity.Member;
import antigravity.entity.Product;
import antigravity.payload.ResponseDto;
import antigravity.repository.LikeRepository;
import antigravity.repository.MemberRepository;
import antigravity.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor

public class ProductService {

    private final ProductRepository productRepository;
    private final MemberRepository memberRepository;
    private final LikeRepository likeRepository;


    //찜상품등록

    @Transactional
    public ResponseDto<?> likeProduct(Long productId, Long memberId) {
        //상품 등록 여부 확인
        Product product = isPresentProduct(productId);
        if (null == product) {
            return ResponseDto.fail(CustomError.PRODUCT_NOT_FOUND.name(), CustomError.PRODUCT_NOT_FOUND.getMessage());
        }
        //멤버 존재 여부 확인
        Member member = isPresentMember(memberId);
        if (null == member) {
            ResponseDto.fail(CustomError.MEMBER_NOT_FOUND.name(),
                    CustomError.MEMBER_NOT_FOUND.getMessage());
        }

        //찜 등록 여부 확인
        Like like = isPresentProductLike(product, member);
        if (null == like) {
            likeRepository.save(
                    Like.builder()
                            .product(product)
                            .member(member)
                            .build()
            );
            //조횟수 증가
            updateView(productId);
            return ResponseDto.success("201 Created");
        } else {
            return ResponseDto.fail(CustomError.ALERADY_LIKE_PRODUCT.name(),
                    CustomError.ALERADY_LIKE_PRODUCT.getMessage());
        }
    }


    @Transactional(readOnly = true)
    public Product isPresentProduct(Long productId) {
        Optional<Product> optionalPost = productRepository.findById(productId);
        return optionalPost.orElse(null);
    }

    @Transactional(readOnly = true)
    public Member isPresentMember(Long memberId) {
        Optional<Member> optionalMember = memberRepository.findById(memberId);
        return optionalMember.orElse(null);
    }


    @Transactional(readOnly = true)
    public Like isPresentProductLike(Product product, Member member) {
        Optional<Like> optionalProductLike = likeRepository.findByProductAndMember(product, member);
        return optionalProductLike.orElse(null);
    }

    //조횟수 증가
    @Transactional
    public int updateView(Long productId) {
        return productRepository.updateView(productId);
    }
}


