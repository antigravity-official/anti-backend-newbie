package antigravity.repository;

import antigravity.entity.WishList;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;

@RequiredArgsConstructor
public class WishListRepositoryImpl implements WishListRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<WishList> getPage(int page) {
//        jpaQueryFactory.selectFrom(QWi)
        return null;
    }
}
