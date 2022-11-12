package antigravity.repository;

import antigravity.entity.Product;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

import static antigravity.entity.QProduct.product;

@Repository
@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<Product> getPage(List<Long> productIds, Pageable pageable) {
        List<Product> content = jpaQueryFactory
                .selectFrom(product)
                .where(
                        product.id.notIn(productIds)
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Product> countQuery = jpaQueryFactory.selectFrom(product);

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchCount);
    }
}
