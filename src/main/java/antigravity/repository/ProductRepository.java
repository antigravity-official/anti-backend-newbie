package antigravity.repository;

import antigravity.entity.Heart;
import antigravity.entity.Product;
import antigravity.entity.ViewCount;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

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

    public Heart isAlreadyLiked(Long memberId, Long productId) {
        String query = "SELECT member_id, product_id FROM heart WHERE member_id = :memberId AND product_id = :productId";
        MapSqlParameterSource params = new MapSqlParameterSource().addValue("memberId", memberId).addValue("productId", productId);

        return jdbcTemplate.queryForObject(query, params, (rs, rowNum) ->
                Heart.builder()
                        .memberId(rs.getLong("member_id"))
                        .productId(rs.getLong("product_id"))
                        .build());
    }

    public int deleteLikedProduct(Long memberId, Long productId) {
        String query = "DELETE FROM heart WHERE member_id = :memberId AND product_id = :productId";
        MapSqlParameterSource params = new MapSqlParameterSource().addValue("memberId", memberId).addValue("productId", productId);;

        return jdbcTemplate.update(query, params);
    }

    public ViewCount checkViewCount(Long productId) {
        String query = "SELECT product_id FROM view_count WHERE product_id = :productId";
        MapSqlParameterSource params = new MapSqlParameterSource().addValue("productId", productId);

        return jdbcTemplate.queryForObject(query, params, (rs, rowNum) ->
                ViewCount.builder()
                        .productId(rs.getLong("product_id"))
                        .build());
    }

    public int updateViewCount(Long productId) {
        String query = "UPDATE view_count SET views = views+1 WHERE product_id = :productId";
        MapSqlParameterSource params = new MapSqlParameterSource().addValue("productId", productId);

        return jdbcTemplate.update(query, params);
    }

    public int addViewCount(Long productId) {
        String query = "INSERT INTO view_count (product_id, views) VALUES (:productId, 1)";
        MapSqlParameterSource params = new MapSqlParameterSource().addValue("productId", productId);

        return jdbcTemplate.update(query, params);
    }

    public int likeProduct(Long memberId, Long productId) {
    String query = "INSERT INTO heart (member_id, product_id) VALUES (:memberId, :productId)";
    MapSqlParameterSource params = new MapSqlParameterSource().addValue("memberId", memberId).addValue("productId", productId);

    return jdbcTemplate.update(query, params);
    }
}
