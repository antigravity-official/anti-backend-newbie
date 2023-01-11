package antigravity.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class favoriteRepository {

    private final JdbcTemplate template1;

    public void saveFavorite(int userId,int productId){
        String insertQuery = "insert into fav (userid,productid) values(?,?)";
        template1.update(insertQuery,userId,productId);
    }

    public void increaseViewcount(int productId){
        String updateQuery = "update product set viewcount = viewcount + 1 where id = ?";
        template1.update(updateQuery,productId);
    }

}
