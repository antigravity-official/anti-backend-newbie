package antigravity.product.repository;

import static antigravity.product.domain.QProduct.product;
import static antigravity.product.domain.QProductLike.productLike;
import static antigravity.product.domain.QProductView.productView;

import antigravity.product.domain.Product;
import antigravity.product.domain.ProductLike;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

@Repository
public class ProductQueryDslRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public ProductQueryDslRepository(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    public Page<ProductResponse> searchAll(Long userId, int page, int size) {
        PageRequest pageRequest = PageRequest.of((page - 1), size);
        List<Product> products = jpaQueryFactory.selectFrom(product)
                .where(product.deletedAt.isNull())
                .limit(pageRequest.getPageSize())
                .offset(pageRequest.getOffset())
                .orderBy(product.id.desc())
                .fetch();

        JPAQuery<Long> countQuery = jpaQueryFactory.select(product.count()).from(product)
                .where(product.deletedAt.isNull());

        return getProductResponses(userId, pageRequest, products, countQuery);
    }

    public Page<ProductResponse> searchWithLiked(Long userId, int page, int size) {
        PageRequest pageRequest = PageRequest.of((page - 1), size);
        List<Long> productIdsWithLiked = jpaQueryFactory.select(productLike.productId)
                .from(productLike)
                .where(productLike.userId.eq(userId)).fetch();

        List<Product> products = jpaQueryFactory.selectFrom(product)
                .where(product.id.in(productIdsWithLiked).and(product.deletedAt.isNull()))
                .limit(size)
                .offset(getOffset(page, size))
                .orderBy(product.id.desc())
                .fetch();

        JPAQuery<Long> countQuery = jpaQueryFactory.select(product.count()).from(product)
                .where(product.id.in(productIdsWithLiked).and(product.deletedAt.isNull()));

        return getProductResponses(userId, pageRequest, products, countQuery);
    }

    public Page<ProductResponse> searchWithDidNotLiked(Long userId, int page, int size) {
        PageRequest pageRequest = PageRequest.of((page - 1), size);
        List<Long> productIdsWithLiked = jpaQueryFactory.select(productLike.productId)
                .from(productLike)
                .where(productLike.userId.eq(userId)).fetch();

        List<Product> products = jpaQueryFactory.selectFrom(product)
                .where(product.id.notIn(productIdsWithLiked).and(product.deletedAt.isNull()))
                .limit(size)
                .offset(getOffset(page, size))
                .orderBy(product.id.desc())
                .fetch();

        JPAQuery<Long> countQuery = jpaQueryFactory.select(product.count()).from(product)
                .where(product.id.notIn(productIdsWithLiked).and(product.deletedAt.isNull()));

        return getProductResponses(userId, pageRequest, products, countQuery);
    }

    private Page<ProductResponse> getProductResponses(Long userId, PageRequest pageRequest,
            List<Product> products, JPAQuery<Long> countQuery) {
        List<ProductResponse> productResponses = products.stream()
                .map(p -> {
                    Long productId = p.getId();
                    List<ProductLike> productLikes = getProductLikesByProductId(productId);
                    Integer totalViews = getTotalViewsByProductId(productId);
                    return ProductResponse.of(userId, p, productLikes, totalViews);
                })
                .collect(Collectors.toList());

        return PageableExecutionUtils.getPage(productResponses, pageRequest, countQuery::fetchOne);
    }

    private List<ProductLike> getProductLikesByProductId(Long productId) {
        return jpaQueryFactory.selectFrom(productLike)
                .where(productLike.productId.eq(productId))
                .fetch();
    }

    private Integer getTotalViewsByProductId(Long productId) {
        return jpaQueryFactory.selectFrom(productView).where(productView.productId.eq(productId))
                .fetch()
                .size();
    }

    private long getOffset(int page, int size) {
        return (long) (page - 1) * size;
    }
}
