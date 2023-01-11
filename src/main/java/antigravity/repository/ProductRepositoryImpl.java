package antigravity.repository;

import static antigravity.entity.QLikedProduct.*;
import static antigravity.entity.QProduct.*;

import java.util.List;

import com.querydsl.jpa.impl.JPAQueryFactory;

import antigravity.entity.Product;
import antigravity.payload.ProductSearch;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepositoryCustom {

	private final JPAQueryFactory queryFactory;

	@Override
	public List<Product> findProducts(ProductSearch productSearch) {
		return queryFactory.selectFrom(product)
				.limit(productSearch.getSize())
				.offset(productSearch.getOffset())
				.fetch();
	}

	@Override
	public List<Product> findProductsLikedByUser(Long userId, ProductSearch productSearch) {
		return queryFactory.selectFrom(product)
				.leftJoin(product.ProductLiked, likedProduct)
				.where(likedProduct.user.id.eq(userId))
				.limit(productSearch.getSize())
				.offset(productSearch.getOffset())
				.orderBy(product.id.desc())
				.fetch();
	}

	@Override
	public List<Product> findProductsUnLikedByUser(Long userId, ProductSearch productSearch) {
		return queryFactory.selectFrom(product)
				.leftJoin(product.ProductLiked, likedProduct)
				.where(likedProduct.user.id.ne(userId))
				.limit(productSearch.getSize())
				.offset(productSearch.getOffset())
				.orderBy(product.id.desc())
				.fetch();
	}
}
