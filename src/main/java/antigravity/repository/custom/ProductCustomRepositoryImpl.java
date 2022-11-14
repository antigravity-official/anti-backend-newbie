package antigravity.repository.custom;

import antigravity.entity.QProduct;
import antigravity.entity.QProductLike;
import antigravity.payload.ProductResponse;
import antigravity.payload.ProductSearchCriteria;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.List;
import static com.querydsl.core.types.ExpressionUtils.count;

@RequiredArgsConstructor
public class ProductCustomRepositoryImpl implements ProductCustomRepository {
    private final JPAQueryFactory query;
    private final QProduct qProduct = QProduct.product;
    private final QProductLike qProductLike = QProductLike.productLike;

    @Override
    public List<ProductResponse> findProductAndWishList(ProductSearchCriteria productSearchCriteria) {
        return makeConditionalQuery(productSearchCriteria).getContent();
    }

    private JPAQuery<ProductResponse> baseQuery(ProductSearchCriteria productSearchCriteria) {
        return query.select(Projections.fields(ProductResponse.class,
                        qProduct.id.as("id"),
                        qProduct.sku.as("sku"),
                        qProduct.name.as("name"),
                        qProduct.price.as("price"),
                        qProduct.quantity.as("quantity"),
                        new CaseBuilder()
                                .when(qProductLike.user.id.eq(productSearchCriteria.getUserId())).then(true)
                                .otherwise(false).as("liked"),
                        ExpressionUtils.as(
                                JPAExpressions.select(count(qProductLike))
                                        .from(qProductLike)
                                        .where(qProductLike.product.id.eq(qProduct.id)),
                                "totalLiked"),
                        qProduct.createdAt.as("createdAt"),
                        qProduct.updatedAt.as("updatedAt")
                ))
                .from(qProduct)
                .leftJoin(qProductLike)
                .on(qProduct.eq(qProductLike.product))
                .fetchJoin();
    }

    private JPAQuery<Long> baseCountQuery(ProductSearchCriteria productSearchCriteria) {
        return query.select(qProduct.count())
                .from(qProduct)
                .leftJoin(qProductLike)
                .on(qProduct.eq(qProductLike.product))
                .fetchJoin()
                .where(containsLikedProduct(productSearchCriteria))
                .groupBy(qProduct.id);
    }

    private Page<ProductResponse> makeConditionalQuery(ProductSearchCriteria productSearchCriteria) {
        List<ProductResponse> result = null;
        Boolean isLiked = productSearchCriteria.getLiked();
        JPAQuery<Long> countQuery = null;
        if( isLiked == Boolean.TRUE || isLiked == null) {
            result = baseQuery(productSearchCriteria)
                    .where(containsLikedProduct(productSearchCriteria))
                    .groupBy(qProduct.id)
                    .offset(calculateOffset(productSearchCriteria))
                    .limit(productSearchCriteria.getSize())
                    .fetch();
            countQuery = baseCountQuery(productSearchCriteria);
        }
        if ( isLiked == Boolean.FALSE )  {
            result = baseQuery(productSearchCriteria)
                    .groupBy(qProduct.id)
                    .having(qProduct.id.notIn(
                                    JPAExpressions.select(qProductLike.product.id)
                                            .from(qProductLike)
                                            .where(
                                                    qProductLike.user.id.notIn(productSearchCriteria.getUserId())
                                            )
                    ))
                    .offset(calculateOffset(productSearchCriteria))
                    .limit(productSearchCriteria.getSize())
                    .fetch();
            countQuery = baseCountQuery(productSearchCriteria)
                    .having(qProduct.id.notIn(
                            JPAExpressions.select(qProduct.id)
                                    .from(qProductLike)
                                    .where(qProductLike.user.id.notIn(productSearchCriteria.getUserId()))
                    ));
        }
        return convertPageableObject(result, countQuery, productSearchCriteria);
    }

    private BooleanExpression containsLikedProduct(ProductSearchCriteria productSearchCriteria) {
        Boolean isLiked = productSearchCriteria.getLiked();
        if( isLiked == Boolean.TRUE ) {
            return qProductLike.user.id.eq(productSearchCriteria.getUserId());
        }
        if ( isLiked == Boolean.FALSE )  {
            return qProductLike.user.id.ne(productSearchCriteria.getUserId()).or(qProductLike.user.isNull());
        }
        return null;
    }

    private PageRequest convertPageRequest(ProductSearchCriteria productSearchCriteria) {
        return PageRequest.of(productSearchCriteria.getPage(), productSearchCriteria.getSize());
    }

    private long calculateOffset(ProductSearchCriteria replySearchCriteria) {
        return (long) replySearchCriteria.getPage() * (long) replySearchCriteria.getSize();
    }

    private Page<ProductResponse> convertPageableObject(List<ProductResponse> result,
                                                        JPAQuery<Long> countQuery,
                                                        ProductSearchCriteria productSearchCriteria) {
        return PageableExecutionUtils.getPage(
                result,
                convertPageRequest(productSearchCriteria),
                () -> countQuery.fetch().size()
        );
    }
}