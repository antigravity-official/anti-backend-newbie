package antigravity.repository.querydsl;

import antigravity.entity.*;
import antigravity.payload.ProductResponse;
import antigravity.payload.QProductResponse;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import java.util.List;

public class BookmarkRepositoryImpl implements BookmarkRepositoryCustom{

    private static final QProduct qProduct = QProduct.product;
    private static final QUser qUser = QUser.user;
    private static final QBookmark qBookmark = QBookmark.bookmark;
    private static final QProductHits qProductHits = QProductHits.productHits;
    private final JPAQueryFactory jpaQueryFactory;

    public BookmarkRepositoryImpl(EntityManager entityManager){
        this.jpaQueryFactory = new JPAQueryFactory(entityManager);
    }

    @Override
    public Page<ProductResponse> findByLikedAndUserId(Boolean liked, Pageable pageable, Long userId) {
        List<ProductResponse> productResponses = jpaQueryFactory
                .select(new QProductResponse(
                        qProduct.id,
                        qProduct.sku,
                        qProduct.name,
                        qProduct.price,
                        qProduct.quantity,
                        eqLiked(liked),
                        qProduct.id.count().intValue(),
                        qProductHits.hits.intValue(),
                        qProduct.createAt,
                        qProduct.updatedAt
                ))
                .from(qProduct)
                .leftJoin(qBookmark)
                .on(qBookmark.productId.eq(qProduct.id))
                .leftJoin(qProductHits)
                .on(qProductHits.productId.eq(qProduct.id))
                .leftJoin(qUser)
                .on(qUser.id.eq(qBookmark.user.id))
                .where(eqUserId(liked,userId))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .groupBy(qProduct.id,
                        qProduct.sku,
                        qProduct.name,
                        qProduct.price,
                        qProduct.quantity,
                        qProductHits.hits.intValue(),
                        qProduct.createAt,
                        qProduct.updatedAt)
                .fetch();

        return new PageImpl<>(productResponses,pageable,pageable.getOffset());
    }

    private Expression<Boolean> eqLiked(Boolean liked){
        if(liked == null){
            return Expressions.asBoolean(true);
        }

        return Expressions.asBoolean(false);
    }

    private BooleanExpression eqBookmark(){
        return qProduct.id.notIn(
                jpaQueryFactory
                        .select(qBookmark.productId)
                        .from(qBookmark));
    }

    private BooleanExpression eqUserId(Boolean liked, Long userId){
        return liked == null ? null: liked ? qUser.id.eq(userId): eqBookmark();
    }

}
