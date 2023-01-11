package antigravity.product.domain.repository;


import antigravity.global.exception.AntiException;
import antigravity.product.domain.entity.Product;
import antigravity.product.exception.ProductErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.util.*;

@RequiredArgsConstructor
@Repository
public class ProductRepository {
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final JdbcTemplate jdbcTemplate;
    GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();
    public void updateViewCntFromRedis(Long productId, Long viewCnt) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("viewCnt", viewCnt);
        params.put("product_id", productId);
        String sql = "update product set viewed = :viewCnt where id = :product_id";
        namedParameterJdbcTemplate.update(sql, params);
    }

    public Long findProductViewCnt(Long productId) {
        String sql = "select viewed from product where id = :product_id";
        MapSqlParameterSource param = new MapSqlParameterSource("product_id", productId);
        return namedParameterJdbcTemplate.queryForObject(sql, param, Long.class);
    }

    public Product findById(Long id) {
        try {
            String query = "SELECT * FROM product WHERE id = :id";
            MapSqlParameterSource params = new MapSqlParameterSource("id", id);
            return namedParameterJdbcTemplate.queryForObject(query, params, productRowMapper());
        }catch (Exception e) {
            throw new AntiException(ProductErrorCode.PRODUCT_NOT_EXIST);
        }
    }

    public Integer countProduct() {
        String sql = "select count(*) from product";
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }

    public Page<Product> findAll(Pageable pageable) {
        Sort.Order order = !pageable.getSort().isEmpty() ? pageable.getSort().toList().get(0) : Sort.Order.by("id");
        List<Product> products = namedParameterJdbcTemplate.query("SELECT * FROM PRODUCT ORDER BY " + order.getProperty() + " " + order.getDirection().name() + " LIMIT " + pageable.getPageSize()
                +" OFFSET "+ pageable.getOffset(),productRowMapper());
        return new PageImpl<Product>(products, pageable, countProduct());
    }
    public Page<Product> findAllNotDipProduct(List<Long> dipProductIds, Pageable pageable) {
        Sort.Order order = !pageable.getSort().isEmpty() ? pageable.getSort().toList().get(0) : Sort.Order.by("id");
        SqlParameterSource parameters = new MapSqlParameterSource("ids", dipProductIds);
        List<Product> products = namedParameterJdbcTemplate.query("SELECT * FROM PRODUCT WHERE id NOT in (:ids) ORDER BY " + order.getProperty() + " " + order.getDirection().name() + " LIMIT " + pageable.getPageSize()
                +" OFFSET "+ pageable.getOffset(), parameters, productRowMapper());
        return new PageImpl<Product>(products, pageable, countProduct());
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
        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(params),generatedKeyHolder);
        return (long)generatedKeyHolder.getKeys().get("id");
    }

    private RowMapper<Product> productRowMapper() {
        return (rs, rowNum) -> Product.builder()
                .id(rs.getLong("id"))
                .sku(rs.getString("sku"))
                .name(rs.getString("name"))
                .price(rs.getBigDecimal("price"))
                .quantity(rs.getInt("quantity"))
                .viewed(rs.getLong("viewed"))
                .createdAt(rs.getTimestamp("created_at").toLocalDateTime())
                .build();
    }
}
