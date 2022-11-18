package antigravity.repository;

import antigravity.entity.Product;
import antigravity.entity.Wish;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.util.Optional.*;

@RequiredArgsConstructor
@Repository
public class ProductRepository {
    private final JdbcTemplate jdbcTemplate;

    public Optional<Product> findById(Long id) {

        String query = "SELECT id, sku, name, price, quantity, view, created_at" +
                "       FROM product WHERE id = ?";
        List<Product> results = jdbcTemplate.query(query, productMapper, new Object[]{id});
        return Optional.ofNullable(results.isEmpty() ? null : results.get(0));
    }

    public void updateViewCount(Product product) {
        String query = "UPDATE product SET view = ? WHERE id = ?";
        jdbcTemplate.update(query, product.getView() + 1, product.getId());
    }

    static RowMapper<Product> productMapper = (rs, rowNum) ->
            Product.builder()
                    .id(rs.getLong("id"))
                    .sku(rs.getString("sku"))
                    .name(rs.getString("name"))
                    .price(rs.getBigDecimal("price"))
                    .quantity(rs.getInt("quantity"))
                    .view(rs.getLong("view"))
                    .createdAt(rs.getTimestamp("created_at").toLocalDateTime())
                    .build();


}
