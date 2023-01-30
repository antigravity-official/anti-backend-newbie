package antigravity.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class favoriteRepository {

    private final JdbcTemplate template1;

    public void saveFavorite(int userId,int productId){

        //user가 존재 하지 않는 경우 구현(추후 refactor 예정)
        String query = "select count(*) from users where id = " + userId;
        int count = template1.queryForObject(query,Integer.class).intValue();
        if(count==0){
            System.out.println("유저가 존재하지 않는 경우");
        }

        String insertQuery = "insert into fav (userid,productid) values(?,?)";
        template1.update(insertQuery,userId,productId);

    }

    public void increaseViewcount(int productId){

        String updateQuery = "update product set viewcount = viewcount + 1 where id = ?";
        template1.update(updateQuery,productId);

    }

}
