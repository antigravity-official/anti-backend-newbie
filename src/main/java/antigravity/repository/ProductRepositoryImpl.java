package antigravity.repository;

import antigravity.entity.Product;
import antigravity.payload.ProductResponse;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static antigravity.entity.QProduct.product;
import static antigravity.entity.QWish.wish;


@Repository
@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepositoryCustom{

    private final JPAQueryFactory query ;
    private final EntityManager em;

    // like 상태 Null인경우

    public Page<ProductResponse> findAllProduct(Long userId, Pageable pageable){
        List<Product> resultQuery = query
                .selectFrom(product)
                .leftJoin(product.wishList, wish)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
        List<ProductResponse> collect = resultQuery.stream().map(p -> new ProductResponse(p, false)).collect(Collectors.toList());
        List<ProductResponse> updateProductList = new ArrayList<>();

        for (ProductResponse product : collect) {
            if(product.getWishList().stream().anyMatch(p->p.getUsers().getId().equals(userId))){
                product.setLiked(true);
            }
            updateProductList.add(product);
        }

        return new PageImpl<>(updateProductList,pageable,getCount());
    }
    @Override
    public Page<ProductResponse> findProductTrueLike(Long userId, Pageable pageable, boolean wishBit) {
        List<Product> resultQuery = query
                .selectFrom(product)
                .join(product.wishList, wish)
                .where(wish.users.id.eq(userId))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        List<ProductResponse> collect = resultQuery.stream().map(p -> new ProductResponse(p,true)).collect(Collectors.toList());

        return new PageImpl<>(collect,pageable,getCount());
    }
    @Override
    public Page<ProductResponse> findProductFalseLike(Long userId, Pageable pageable) {
        List<Product> resultQuery = query
                .selectFrom(product)
                .leftJoin(product.wishList, wish)
                .where(wish.users.id.ne(userId).or(wish.users.id.isNull()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
        List<ProductResponse> collect = resultQuery.stream().map(p -> new ProductResponse(p,false)).collect(Collectors.toList());
        return new PageImpl<>(collect,pageable,getCount());
    }
    private Long getCount() {
        return query
                .select(product.count())
                .from(product)
                .fetchOne();
    }

}
