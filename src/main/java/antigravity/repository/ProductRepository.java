package antigravity.repository;

import antigravity.entity.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Repository
public class ProductRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    // 예시 메서드입니다.
    public Product findById(Long id) throws EmptyResultDataAccessException {
        String query = "SELECT id, sku, name, price, viewed, quantity, created_at" +
                "       FROM product WHERE id = :id";
        MapSqlParameterSource params = new MapSqlParameterSource("id", id);

        return jdbcTemplate.queryForObject(query, params, (rs, rowNum) ->
                Product.builder()
                        .id(rs.getLong("id"))
                        .sku(rs.getString("sku"))
                        .name(rs.getString("name"))
                        .price(rs.getBigDecimal("price"))
                        .viewed(rs.getLong("viewed"))
                        .quantity(rs.getInt("quantity"))
                        .createdAt(rs.getTimestamp("created_at").toLocalDateTime())
                        .build());
    }

    public int findUser(String userNo) {
        String query = "SELECT COUNT(*) " +
                "       FROM `user` " +
                "       WHERE id = :id";
        MapSqlParameterSource params = new MapSqlParameterSource("id", Long.parseLong(userNo));
        return jdbcTemplate.queryForObject(query, params, Integer.class);
    }

    public int findById(String productNo) {
        String query = "SELECT COUNT(*) " +
                "       FROM `product` WHERE id = :id";
        MapSqlParameterSource params = new MapSqlParameterSource("id", Long.parseLong(productNo));
        return jdbcTemplate.queryForObject(query, params, Integer.class);
    }

    public int insertLikeProduct(String userNo, String productId) {
        Map<String, String> map = new HashMap<>();
        map.put("userNo", userNo);
        map.put("productId", productId);

        String query = "INSERT INTO `like` " +
                "       VALUES ( :userNo, :productId ) ";
        MapSqlParameterSource params = new MapSqlParameterSource(map);
        return jdbcTemplate.update(query, params);
    }

    public int testSelectLike() {
        String query = "SELECT COUNT(*) " +
                "       FROM `like` "
                +
                "       WHERE 1 = :number ";
        MapSqlParameterSource params = new MapSqlParameterSource("number", 1);
        return jdbcTemplate.queryForObject(query, params, Integer.class);
    }

    public int likeCheckSum(String userNo, String productId) {
        Map<String, String> map = new HashMap<>();
        map.put("userNo", userNo);
        map.put("productId", productId);

        String query = "SELECT COUNT(*) " +
                "       FROM `like` " +
                "       WHERE user_id = :userNo " +
                "       AND item_id = :productId";
        MapSqlParameterSource params = new MapSqlParameterSource(map);
        return jdbcTemplate.queryForObject(query, params, Integer.class);
    }

    public void updateView(String productId) {
        String query = "UPDATE `product` " +
                "       SET `viewed` = `viewed` + 1" +
                "       WHERE id = :productId";
        MapSqlParameterSource param = new MapSqlParameterSource("productId", productId);
        jdbcTemplate.update(query, param);
    }

    public List<Product> selectLikeProduct(String userNo, Integer page, Integer size) {
        String query = "SELECT * " +
                "       FROM `product` p" +
                "       WHERE p.id IN (SELECT item_id" +
                "                      FROM `like`" +
                "                      WHERE user_id = :userNo)" +
                "       ORDER BY p.id LIMIT :size OFFSET (:size * (:page - 1))";
        return createParamMap(userNo, page, size, query);
    }

    public List<Product> selectDontLikeProduct(String userNo, Integer page, Integer size) {
        String query = "SELECT * " +
                "       FROM `product` p" +
                "       WHERE p.id NOT IN (SELECT item_id" +
                "                           FROM `like`" +
                "                           WHERE user_id = :userNo)" +
                "       ORDER BY p.id LIMIT :size OFFSET (:size * (:page - 1))" ;
        return createParamMap(userNo, page, size, query);
    }

    private List<Product> createParamMap(String userNo, Integer page, Integer size, String query) {
        Map<String, String> map = new HashMap<>();
        map.put("userNo", userNo);
        map.put("page", String.valueOf(page));
        map.put("size", String.valueOf(size));

        return getProducts(new MapSqlParameterSource(map), query);
    }

    private List<Product> getProducts(MapSqlParameterSource param, String query) {

        return jdbcTemplate.query(query, param, (rs, rowNum) ->
                Product.builder()
                        .id(rs.getLong("id"))
                        .sku(rs.getString("sku"))
                        .name(rs.getString("name"))
                        .price(rs.getBigDecimal("price"))
                        .viewed(rs.getLong("viewed"))
                        .quantity(rs.getInt("quantity"))
                        .createdAt(rs.getTimestamp("created_at").toLocalDateTime())
                        .build());
    }
}
