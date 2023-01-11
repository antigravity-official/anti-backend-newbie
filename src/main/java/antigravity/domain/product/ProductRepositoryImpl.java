package antigravity.domain.product;

import static antigravity.domain.product.QProduct.product;
import static antigravity.domain.product.QProductLike.productLike;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<Product> findProductsByStatus(Long memberId, LikeStatus likeStatus, Pageable pageable) {

        List<Product> products = queryFactory.selectFrom(product)
            .leftJoin(productLike)
            .on(product.id.eq(productLike.product.id))
            .where(likeCondition(memberId, likeStatus))
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();

        long totalCount = queryFactory.select(product.count())
            .from(product)
            .leftJoin(productLike)
            .on(product.id.eq(productLike.product.id))
            .where(likeCondition(memberId, likeStatus))
            .fetchFirst();

        return new PageImpl<>(products, pageable, totalCount);
    }

    private BooleanExpression likeCondition(Long memberId, LikeStatus likeStatus) {

        if (LikeStatus.TRUE.equals(likeStatus)) {
            return productLike.member.id.eq(memberId);
        }

        if (LikeStatus.FALSE.equals(likeStatus)) {
            return product.id.notIn(getLikedProductIds(memberId));
        }

        return null;
    }

    private JPAQuery<Long> getLikedProductIds(Long memberId) {
        return queryFactory.select(productLike.product.id).from(productLike.product)
            .where(productLike.member.id.eq(memberId));
    }

}
