package antigravity.product.domain.repository;


import antigravity.product.domain.entity.DipProduct;
import antigravity.product.domain.entity.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class ProductRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;
    GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();
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

    public Long save(Product product) {
        String sql = "insert into product (sku, name, price, quantity, viewed, created_at) values (:sku, :name, :price, :quantity, :viewed, :createdAt)";
        Map<String, Object> params = new HashMap<>();
        params.put("sku", product.getSku());
        params.put("name", product.getName());
        params.put("price", product.getPrice());
        params.put("quantity", product.getQuantity());
        params.put("viewed", product.getViewed());
        params.put("createdAt", product.getCreatedAt());
        jdbcTemplate.update(sql, new MapSqlParameterSource(params),generatedKeyHolder);
        return (long)generatedKeyHolder.getKeys().get("id");
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
