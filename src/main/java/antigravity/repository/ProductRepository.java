package antigravity.repository;

import antigravity.entity.Favorite;
import antigravity.entity.Product;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.relational.core.sql.In;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Repository
public class ProductRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    // 예시 메서드입니다.
    public Product findById(Long id) {
        try{
            String query = "SELECT id, sku, name, price, quantity, created_at" +
                    "       FROM product WHERE id = :id";
            MapSqlParameterSource params = new MapSqlParameterSource("id", id);

            return jdbcTemplate.queryForObject(query, params, (rs, rowNum) ->
                    Product.builder()
                            .id(rs.getLong("id"))
                            .sku(rs.getString("sku"))
                            .name(rs.getString("name"))
                            .price(rs.getBigDecimal("price"))
                            .quantity(rs.getInt("quantity"))
                            .createdAt(rs.getTimestamp("created_at").toLocalDateTime())
                            .build());

        }catch (EmptyResultDataAccessException error){
            return null;
        }

    }

    // 중복 코드 제거 필요
    /**
     * 상품 좋아요 Repository
     * @date 2023.01.09
     * @author 이상우
     * @param productId
     * @param userId
     * @return String
    * */
    public String productLiked(Long userId, Long productId){
        // 결과를 담는 변수
        String result;

        try{
            // 찜 누른 상품이 존재하는 경우
            String selectQuery = "SELECT product_id" +
                    "             FROM favorite" +
                    "             WHERE user_id = :userId" +
                    "             AND product_id = :productId";

            Map<String,Long> selectParms = new HashMap<>();

            selectParms.put("userId", userId);
            selectParms.put("productId", productId);

            Long favoriUserId = jdbcTemplate.queryForObject(selectQuery, selectParms, Long.class);

            result = "400";

        }catch (EmptyResultDataAccessException error){
            // 찜한 상품의 정보가 없을때
            String createQuery = "INSERT INTO favorite(product_id, user_id)" +
                    "             VALUES ("+productId+", "+userId+")";

            Map<String,Long> createParam = new HashMap<>();

            int favoriteResult = jdbcTemplate.update(createQuery, createParam);

            if(favoriteResult <= 0){
                result = "506";
            }

            String updateQuery = "UPDATE product " +
                    "       SET totalLiked=totalLiked+1," +
                    "           viewed = viewed+1 " +
                    "       WHERE id = :productId";

            MapSqlParameterSource updatePram = new MapSqlParameterSource("productId", productId);

            int value = jdbcTemplate.update(updateQuery, updatePram);

            if(value <= 0){
                result= "506";
            }else{
                result = "201";
            }

        }

        return result;
    }
}
