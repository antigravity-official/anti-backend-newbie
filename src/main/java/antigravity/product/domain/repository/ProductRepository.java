package antigravity.product.domain.repository;


import antigravity.product.domain.entity.DipProduct;
import antigravity.product.domain.entity.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class ProductRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;
    public void updateViewCntFromRedis(Long productId, Long viewCnt) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("viewCnt", viewCnt);
        params.put("product_id", productId);
        String sql = "update product set viewed = :viewCnt where id = :product_id";
        jdbcTemplate.update(sql, params);
    }

    public Long findProductViewCnt(Long productId) {
        String sql = "select viewed from product where id = :product_id";
        MapSqlParameterSource param = new MapSqlParameterSource("product_id", productId);
        return jdbcTemplate.queryForObject(sql, param, Long.class);
    }

    public Optional<Product> findById(Long id) {
        String query = "SELECT * FROM product WHERE id = :id";
        MapSqlParameterSource params = new MapSqlParameterSource("id", id);
        return Optional.ofNullable(jdbcTemplate.queryForObject(query, params, productRowMapper()));
    }
    private RowMapper<Product> productRowMapper() {
        return (rs, rowNum) -> {
            Product product = Product.builder()
                    .id(rs.getLong("id"))
                    .sku(rs.getString("sku"))
                    .name(rs.getString("name"))
                    .price(rs.getBigDecimal("price"))
                    .quantity(rs.getInt("quantity"))
                    .viewed(rs.getLong("viewed"))
                    .createdAt(rs.getTimestamp("created_at").toLocalDateTime())
                    .build();
            return product;
        };
    }
}
