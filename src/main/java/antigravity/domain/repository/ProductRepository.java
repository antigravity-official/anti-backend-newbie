package antigravity.domain.repository;

import antigravity.domain.entity.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;



public interface ProductRepository extends JpaRepository<Product,Long>,ProductRepositoryCustom {

//    private final NamedParameterJdbcTemplate jdbcTemplate;
//
//    // 예시 메서드입니다.
//    public Product findById(Long id) {
//        String query = "SELECT id, sku, name, price, quantity, created_at" +
//                "       FROM product WHERE id = :id";
//        MapSqlParameterSource params = new MapSqlParameterSource("id", id);
//
//        return jdbcTemplate.queryForObject(query, params, (rs, rowNum) ->
//                Product.builder()
//                        .id(rs.getLong("id"))
//                        .sku(rs.getString("sku"))
//                        .name(rs.getString("name"))
//                        .price(rs.getBigDecimal("price"))
//                        .quantity(rs.getInt("quantity"))
//                        .createdAt(rs.getTimestamp("created_at").toLocalDateTime())
//                        .build());
//    }

}
