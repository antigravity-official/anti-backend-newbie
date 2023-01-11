package antigravity.repository;

import antigravity.entity.Product;
import antigravity.payload.ProductResponse;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class ProductRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    // 예시 메서드입니다.
    public Product findById(Long id) {
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
    }

    public int createLike(Long userId, Long productId) throws SQLException {
        String query = "INSERT INTO liked (userId, productId) values (:userId, :productId)";
        Map<String, Long> map = new HashMap<String, Long>() {{
            put("userId", userId);
            put("productId", productId);
        }};
        MapSqlParameterSource params = new MapSqlParameterSource(map);
        return jdbcTemplate.update(query, params);
    }

    public void increaseHits(Long productId) throws SQLException {
        String query = "UPDATE product SET hits = hits + 1 WHERE id = :id";
        MapSqlParameterSource params = new MapSqlParameterSource("id", productId);
        jdbcTemplate.update(query, params);
    }

    public List<ProductResponse> findByLiked(Long userId) {

        MapSqlParameterSource params = new MapSqlParameterSource("id", userId);
        return jdbcTemplate.query(getQueryFindByLiked(true), params, productResponseRowMapper());


    }

    public List<ProductResponse> findAll(Long userId, String page, String size) {
        String query = "SELECT P.*, 'true' as liked, COALESCE(N.TOTALLIKED ,0) as totalliked FROM PRODUCT AS P LEFT OUTER JOIN\n"
                + "(SELECT PRODUCTID, COUNT(ID) AS TOTALLIKED FROM (SELECT LIKED.* FROM PRODUCT JOIN LIKED ON PRODUCT.ID = LIKED.PRODUCTID) GROUP BY PRODUCTID) AS N\n"
                + "ON P.ID = N.PRODUCTID WHERE id in (SELECT productId FROM liked where userId = :id)\n"
                + "union\n"
                + "SELECT P.*, 'false' as liked, COALESCE(N.TOTALLIKED ,0) as totalliked FROM PRODUCT AS P LEFT OUTER JOIN\n"
                + "(SELECT PRODUCTID, COUNT(ID) AS TOTALLIKED FROM (SELECT LIKED.* FROM PRODUCT JOIN LIKED ON PRODUCT.ID = LIKED.PRODUCTID) GROUP BY PRODUCTID) AS N\n"
                + "ON P.ID = N.PRODUCTID WHERE id not in (SELECT productId FROM liked where userId = :id)";

        MapSqlParameterSource params = new MapSqlParameterSource("id", userId);
        return jdbcTemplate.query(query, params, productResponseRowMapper());
    }

    public List<ProductResponse> findByNotLiked(Long userId) {
        MapSqlParameterSource params = new MapSqlParameterSource("id", userId);
        return jdbcTemplate.query(getQueryFindByLiked(false), params, productResponseRowMapper());

    }

    public RowMapper<ProductResponse> productResponseRowMapper () {
        return (rs, rowNum) -> {
            ProductResponse productResponse = ProductResponse.builder()
                    .id(rs.getLong("id"))
                    .sku(rs.getString("sku"))
                    .name(rs.getString("name"))
                    .price(rs.getBigDecimal("price"))
                    .quantity(rs.getInt("quantity"))
                    .liked(rs.getBoolean("liked"))
                    .totalLiked(rs.getInt("totalLiked"))
                    .viewed(rs.getInt("hits"))
                    .createdAt(rs.getTimestamp("created_at").toLocalDateTime())
                    .updatedAt(rs.getTimestamp("updated_at").toLocalDateTime())
                    .build();
            return productResponse;
        };
    }

    private String getQueryFindByLiked (boolean isLiked) {
        String bool = "true";
        if(!isLiked) {
            bool = "false";
        }
        String query = "SELECT P.*, " + bool + " as liked, " + "COALESCE(N.TOTALLIKED ,0) as totalliked FROM PRODUCT AS P LEFT OUTER JOIN \n"
                + "(SELECT PRODUCTID, COUNT(ID) AS TOTALLIKED FROM (SELECT LIKED.* FROM PRODUCT JOIN LIKED ON PRODUCT.ID = LIKED.PRODUCTID) GROUP BY PRODUCTID) AS N\n"
                + "ON P.ID = N.PRODUCTID WHERE id";
        if(!isLiked) {
            query += " not";
        }
        query += " in (SELECT productId FROM liked where userId = :id)";

        return query;
    }
 }
