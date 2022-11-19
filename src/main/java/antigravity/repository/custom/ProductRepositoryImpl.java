package antigravity.repository.custom;

import antigravity.entity.Product;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;

import java.util.ArrayList;
import java.util.List;

import static antigravity.entity.QLikeProduct.*;
import static antigravity.entity.QProduct.product;
import static antigravity.entity.QUser.*;

public class ProductRepositoryImpl {
    private JPAQueryFactory jpaQueryFactory;

    public ProductRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }


    public Slice<Product> likeGet(Boolean liked, Long userId, Pageable pageable) {

        List<Product> like = jpaQueryFactory
                .selectFrom(product)
                .leftJoin(likeProduct).on(product.id.eq(likeProduct.productId))
                .leftJoin(user).on(likeProduct.userId.eq(user.id))
                .where(product.deletedAt.isNull()
                        .and(likeProduct.userId.eq(userId))
                        .and(product.id.eq(likeProduct.productId)))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize() + 1)
                .fetch();

        List<Product> result = new ArrayList<>();
        for (Product likes : like) {
            result = jpaQueryFactory
                    .selectFrom(product)
                    .where(product.ne(likes))
                    .offset(pageable.getOffset())
                    .limit(pageable.getPageSize() + 1)
                    .fetch();
        }

        List<Product> empty = jpaQueryFactory
                .selectFrom(product)
                .where(product.deletedAt.isNull())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize() + 1)
                .fetch();


        boolean hasNext = false;

        if (liked == null) {
           return getPageSize(empty,pageable);
        } else if (liked) {
            return getPageSize(like,pageable);
        } else {
            return getPageSize(result,pageable);
        }
    }

    public SliceImpl<Product> getPageSize(List<Product> box, Pageable pageable) {
        boolean hasNext = false;
        if (box.size() > pageable.getPageSize()) {
            box.remove(pageable.getPageSize());
            hasNext = true;
        }
        return new SliceImpl<>(box, pageable, hasNext);
    }

}
