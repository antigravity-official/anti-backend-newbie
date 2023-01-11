package antigravity.repository;

import antigravity.entity.Heart;
import antigravity.entity.Product;
import antigravity.entity.ViewCount;
import antigravity.payload.ProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class ProductRepository {

    private final JdbcTemplate jdbc;
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

    public List<Heart> isAlreadyLiked(Long memberId, Long productId) {
        String query = "SELECT member_id, product_id FROM heart WHERE member_id = :memberId AND product_id = :productId";
        MapSqlParameterSource params = new MapSqlParameterSource().addValue("memberId", memberId)
                                                                    .addValue("productId", productId);

        return jdbcTemplate.query(query, params, (rs, rowNum) ->
                Heart.builder()
                        .memberId(rs.getLong("member_id"))
                        .productId(rs.getLong("product_id"))
                        .build());
    }

    public int deleteLikedProduct(Long memberId, Long productId) {
        String query = "DELETE FROM heart WHERE member_id = :memberId AND product_id = :productId";
        MapSqlParameterSource params = new MapSqlParameterSource().addValue("memberId", memberId)
                                                                    .addValue("productId", productId);

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
        MapSqlParameterSource params = new MapSqlParameterSource().addValue("memberId", memberId)
                                                                    .addValue("productId", productId);

        return jdbcTemplate.update(query, params);
    }

    public List<ProductResponse> getAllProduct(Long memberId, int listSize, int startList) {
        String query = "SELECT" +
                            "p.id, p.sku, p.name, p.price, p.quantity," +
                            "NVL(h.member_id, 0) as heart_check, hearts, NVL(v.views, 0) as views," +
                            "p.created_at, p.updated_at" +
                        "FROM product p" +
                        "LEFT JOIN" +
                            "(SELECT" +
                                "p2.id, COUNT(h2.product_id) as hearts" +
                            "FROM product p2" +
                            "LEFT JOIN heart h2" +
                            "ON (p2.id = h2.product_id)" +
                            "GROUP BY p2.id) ph" +
                        "USING (id)" +
                        "LEFT JOIN view_count v" +
                        "ON (p.id = v.product_id)" +
                        "LEFT JOIN" +
                            "(SELECT" +
                                "product_id, member_id" +
                            "FROM heart" +
                            "WHERE member_id = :memberId) h" +
                        "ON (p.id = h.product_id)" +
                        "ORDER BY p.created_at desc" +
                        "LIMIT :startList, :listSize";
        MapSqlParameterSource params = new MapSqlParameterSource().addValue("memberId", memberId)
                                                                    .addValue("startList", startList)
                                                                    .addValue("listSize", listSize);

        return jdbcTemplate.query(query, params, (rs, rowNum) ->
                ProductResponse.builder()
                        .id(rs.getLong("id"))
                        .sku(rs.getString("sku"))
                        .name(rs.getString("name"))
                        .price(rs.getBigDecimal("price"))
                        .quantity(rs.getInt("quantity"))
                        .memberId(rs.getLong("heart_check"))
                        .liked(false)
                        .totalLiked(rs.getInt("hearts"))
                        .viewed(rs.getInt("views"))
                        .createdAt(rs.getTimestamp("created_at").toLocalDateTime())
                        .updatedAt(rs.getTimestamp("updated_at").toLocalDateTime())
                        .build());
    }

    public List<ProductResponse> getLikedProduct(Long memberId, int listSize, int startList) {
        String query = "SELECT" +
                            "p.id, p.sku, p.name, p.price, p.quantity," +
                            "NVL(h.member_id, 0) as heart_check, hearts, NVL(v.views, 0) as views," +
                            "p.created_at, p.updated_at" +
                        "FROM product p" +
                        "LEFT JOIN" +
                            "(SELECT" +
                                "p2.id, COUNT(h2.product_id) as hearts" +
                            "FROM product p2" +
                            "LEFT JOIN heart h2" +
                            "ON (p2.id = h2.product_id)" +
                            "GROUP BY p2.id) ph" +
                        "USING (id)" +
                        "LEFT JOIN view_count v" +
                        "ON (p.id = v.product_id)" +
                        "LEFT JOIN heart h" +
                        "ON (p.id = h.product_id)" +
                        "WHERE h.member_id = :memberId" +
                        "ORDER BY p.created_at desc" +
                        "LIMIT :startList, :listSize";

        MapSqlParameterSource params = new MapSqlParameterSource().addValue("memberId", memberId)
                                                                    .addValue("startList", startList)
                                                                    .addValue("listSize", listSize);

        return jdbcTemplate.query(query, params, (rs, rowNum) ->
                ProductResponse.builder()
                        .id(rs.getLong("id"))
                        .sku(rs.getString("sku"))
                        .name(rs.getString("name"))
                        .price(rs.getBigDecimal("price"))
                        .quantity(rs.getInt("quantity"))
                        .liked(true)
                        .totalLiked(rs.getInt("hearts"))
                        .viewed(rs.getInt("views"))
                        .createdAt(rs.getTimestamp("created_at").toLocalDateTime())
                        .updatedAt(rs.getTimestamp("updated_at").toLocalDateTime())
                        .build());
    }

    public List<ProductResponse> getNotLikedProduct(Long memberId, int listSize, int startList) {
        String query = "SELECT" +
                            "p.id, p.sku, p.name, p.price, p.quantity," +
                            "NVL(h.member_id, 0) as heart_check, hearts, NVL(v.views, 0) as views," +
                            "p.created_at, p.updated_at" +
                        "FROM product p" +
                        "LEFT JOIN" +
                            "(SELECT" +
                                "p2.id, COUNT(h2.product_id) as hearts" +
                            "FROM product p2" +
                            "LEFT JOIN heart h2" +
                            "ON (p2.id = h2.product_id)" +
                            "GROUP BY p2.id) ph" +
                        "USING (id)" +
                        "LEFT JOIN view_count v" +
                        "ON (p.id = v.product_id)" +
                        "LEFT JOIN heart h" +
                        "ON (p.id = h.product_id)" +
                        "WHERE h.member_id != :memberId or h.member_id is null" +
                        "ORDER BY p.created_at desc" +
                        "LIMIT :startList, :listSize";

        MapSqlParameterSource params = new MapSqlParameterSource().addValue("memberId", memberId)
                .addValue("startList", startList)
                .addValue("listSize", listSize);

        return jdbcTemplate.query(query, params, (rs, rowNum) ->
                ProductResponse.builder()
                        .id(rs.getLong("id"))
                        .sku(rs.getString("sku"))
                        .name(rs.getString("name"))
                        .price(rs.getBigDecimal("price"))
                        .quantity(rs.getInt("quantity"))
                        .liked(false)
                        .totalLiked(rs.getInt("hearts"))
                        .viewed(rs.getInt("views"))
                        .createdAt(rs.getTimestamp("created_at").toLocalDateTime())
                        .updatedAt(rs.getTimestamp("updated_at").toLocalDateTime())
                        .build());
    }
}
