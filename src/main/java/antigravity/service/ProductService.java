package antigravity.service;

import antigravity.controller.handler.CustomError;
import antigravity.entity.Like;
import antigravity.entity.Member;
import antigravity.entity.Product;
import antigravity.payload.ProductResponse;
import antigravity.payload.ResponseDto;
import antigravity.repository.LikeRepository;
import antigravity.repository.MemberRepository;
import antigravity.repository.ProductRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static antigravity.entity.QProduct.product;
import static antigravity.entity.QLike.like;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor

public class ProductService {

    private final ProductRepository productRepository;
    private final MemberRepository memberRepository;
    private final LikeRepository likeRepository;
    private final JPAQueryFactory jpaQueryFactory;


    //찜상품등록

    @Transactional
    public ResponseDto<?> likeProduct(Long productId, Long memberId) {
        //상품 등록 여부 확인
        Product product = isPresentProduct(productId);
        if (null == product || product.getDeletedAt() == null) {
            return ResponseDto.fail(CustomError.PRODUCT_NOT_FOUND.name(), CustomError.PRODUCT_NOT_FOUND.getMessage());
        }

        //멤버 존재 여부 확인
        Member member = isPresentMember(memberId);
        if (null == member || member.getDeletedAt() == null) {
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
            return ResponseDto.success(like.getId());
        } else {
            return ResponseDto.fail(CustomError.ALERADY_LIKE_PRODUCT.name(),
                    CustomError.ALERADY_LIKE_PRODUCT.getMessage());
        }
    }


    @Transactional(readOnly = true)
    public ResponseDto<?> getLikeProduct(Boolean liked, Pageable pageable, Long memberId) {

        //멤버 존재 여부 확인
        Member member = isPresentMember(memberId);
        if (null == member) {
            ResponseDto.fail(CustomError.MEMBER_NOT_FOUND.name(),
                    CustomError.MEMBER_NOT_FOUND.getMessage());
        }

        if (liked) {
            List<Product> likeProductList = jpaQueryFactory
                    .selectFrom(product).innerJoin(like).on(product.id.eq(like.product.id).and(product.deletedAt.isNotNull()))
                    .where(like.member.id.eq(memberId))
                    .offset(pageable.getOffset())
                    .limit(pageable.getPageSize())
                    .fetch();
            List<ProductResponse> productResponseList = new ArrayList<>();
            for (Product likeProduct : likeProductList) {
                productResponseList.add(ProductResponse.builder(
                        ).id(likeProduct.getId())
                        .sku(likeProduct.getSku())
                        .name(likeProduct.getName())
                        .price(likeProduct.getPrice())
                        .quantity(likeProduct.getQuantity())
                        .viewed(likeProduct.getViewed())
                        .createdAt(likeProduct.getCreatedAt())
                        .updatedAt(likeProduct.getUpdatedAt())
                        .build());

            }
            return ResponseDto.success(productResponseList);
        } else if (!liked) {
            List<Product> likeProductList = jpaQueryFactory
                    .selectFrom(product).leftJoin(like).on(product.id.eq(like.product.id)
                            .and(product.deletedAt.isNotNull())
                            .and(like.member.id.eq(memberId)))
                    .where(like.id.isNull())
                    .offset(pageable.getOffset())
                    .limit(pageable.getPageSize())
                    .fetch();
            List<ProductResponse> productResponseList = new ArrayList<>();
            for (Product likeProduct : likeProductList) {
                productResponseList.add(ProductResponse.builder(
                        ).id(likeProduct.getId())
                        .sku(likeProduct.getSku())
                        .name(likeProduct.getName())
                        .price(likeProduct.getPrice())
                        .quantity(likeProduct.getQuantity())
                        .viewed(likeProduct.getViewed())
                        .createdAt(likeProduct.getCreatedAt())
                        .updatedAt(likeProduct.getUpdatedAt())
                        .build());

            }
            return ResponseDto.success(productResponseList);
        } else {
            List<Product> productList = productRepository.findAll();
            List<ProductResponse> productResponseList = new ArrayList<>();
            for (Product likeProduct : productList) {
                productResponseList.add(ProductResponse.builder(
                        ).id(likeProduct.getId())
                        .sku(likeProduct.getSku())
                        .name(likeProduct.getName())
                        .price(likeProduct.getPrice())
                        .quantity(likeProduct.getQuantity())
                        .viewed(likeProduct.getViewed())
                        .createdAt(likeProduct.getCreatedAt())
                        .updatedAt(likeProduct.getUpdatedAt())
                        .build());

            }
            return ResponseDto.success(productResponseList);


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


