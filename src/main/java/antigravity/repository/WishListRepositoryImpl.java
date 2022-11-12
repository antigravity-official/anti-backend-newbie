package antigravity.repository;

import antigravity.entity.WishList;
import antigravity.enums.Like;
import antigravity.payload.ProductResponse;
import antigravity.payload.QProductResponse;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

import static antigravity.entity.QProduct.product;
import static antigravity.entity.QWishList.wishList;

@Repository
@RequiredArgsConstructor
public class WishListRepositoryImpl implements WishListRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<ProductResponse> getPage(String userId, Pageable pageable) {
        List<ProductResponse> content = jpaQueryFactory
                .select(new QProductResponse(
                        product.id,
                        product.sku,
                        product.name,
                        product.price,
                        product.quantity,
                        wishList.liked,
                        product.totalLiked,
                        product.viewed,
                        product.createdAt,
                        product.updatedAt
                ))
                .from(wishList)
                .join(wishList.product, product)
                .where(
                        wishList.user.id.eq(Long.valueOf(userId)),
                        wishList.liked.eq(Like.TRUE)
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<WishList> countQuery = jpaQueryFactory.selectFrom(wishList);

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchCount);

    }
}
