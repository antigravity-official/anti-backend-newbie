package antigravity.repository;

import antigravity.entity.WishList;
import org.springframework.data.domain.Page;

public interface WishListRepositoryCustom {

    Page<WishList> getPage(int page);
}
