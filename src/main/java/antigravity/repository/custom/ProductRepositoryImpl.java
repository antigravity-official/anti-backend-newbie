package antigravity.repository.custom;

import antigravity.dto.payload.ProductResponse;
import antigravity.entity.Product;
import antigravity.entity.QLikeProduct;
import antigravity.entity.QUser;
import com.querydsl.jpa.impl.JPAQueryFactory;

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


    public List<Product> likeGet(Boolean liked, Long userId) {

        List<Product> like = jpaQueryFactory
                .selectFrom(product)
                .leftJoin(likeProduct).on(product.id.eq(likeProduct.productId))
                .leftJoin(user).on(likeProduct.userId.eq(user.id))
                .where(product.deletedAt.isNull()
                        .and(likeProduct.userId.eq(userId))
                        .and(product.id.eq(likeProduct.productId)))
                .fetch();

        List<Product> result = new ArrayList<>();
        for (Product likes : like) {
            result = jpaQueryFactory
                    .selectFrom(product)
                    .where(product.ne(likes))
                    .fetch();
        }

        List<Product> empty = jpaQueryFactory
                .selectFrom(product)
                .where(product.deletedAt.isNull())
                .fetch();

        if (liked == null) {
            return empty;
        } else if (liked) {
            return like;
        } else {
            return result;
        }
    }
}
