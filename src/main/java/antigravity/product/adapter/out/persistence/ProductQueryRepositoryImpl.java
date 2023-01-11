package antigravity.product.adapter.out.persistence;

import static antigravity.product.domain.QLike.like;
import static antigravity.product.domain.QProduct.product;

import antigravity.product.application.port.in.dto.GetProductsOutput;
import antigravity.product.adapter.out.persistence.cond.GetProductsCond;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
class ProductQueryRepositoryImpl implements ProductQueryRepository {

	private final JPAQueryFactory queryFactory;

	@Override
	public Page<GetProductsOutput> getProducts(Pageable pageable, GetProductsCond cond) {

		List<GetProductsOutput> list = queryFactory
			.select(Projections.fields(GetProductsOutput.class,
				product.id,
				product.sku,
				product.name,
				product.price,
				product.quantity,
				new CaseBuilder()
					.when(like.liked.isNull())
					.then(false)
					.otherwise(like.liked)
					.as("liked"),
				ExpressionUtils.as(
					JPAExpressions.select(like.count().castToNum(Integer.class))
						.from(like)
						.where(like.product.eq(product), like.liked.isTrue())
					, "totalLiked"
				),
				product.likes.size().as("viewed"),
				product.createdAt,
				product.updatedAt
			))
			.from(product)
			.leftJoin(product.likes, like)
			.on(userIdEq(cond.getUserId()))
			.where(likedEq(cond.getLiked()))
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.fetch();

		JPAQuery<Long> countQuery = queryFactory
			.select(product.count())
			.from(product)
			.leftJoin(product.likes, like)
			.where(likedEq(cond.getLiked())
			);

		return PageableExecutionUtils.getPage(list, pageable,
			countQuery::fetchOne);
	}

	private BooleanExpression likedEq(Boolean liked) {
		return liked == null ? null : liked ? like.liked.isTrue() : null;
	}

	private BooleanBuilder userIdEq(Long userId) {
		BooleanBuilder builder = new BooleanBuilder();

		if (userId != null) {
			builder.and(like.userId.eq(userId));
		}

		return builder;
	}

}
