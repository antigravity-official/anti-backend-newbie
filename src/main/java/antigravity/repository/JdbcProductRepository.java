package antigravity.repository;

import antigravity.entity.Product;
import antigravity.payload.ProductRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class JdbcProductRepository implements ProductRepository {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public Optional<Product> findById(Long id) {

        String query = "SELECT id, sku, name, price, quantity, view, created_at, updated_at" +
                "       FROM product WHERE id = ? FOR UPDATE";
        List<Product> results = jdbcTemplate.query(query, productMapper, new Object[]{id});
        return Optional.ofNullable(results.isEmpty() ? null : results.get(0));
    }

    @Override
    public void updateViewCount(Product product) {
        String query = "UPDATE product SET view = ?, updated_at = ? WHERE id = ?";
        jdbcTemplate.update(query, product.getView() + 1, LocalDateTime.now(), product.getId());
    }

    @Override
    public List<Product> getProductList(ProductRequest request) {
        String query = "SELECT product.id, sku, product.name, price, quantity, view, product.created_at, updated_at" +
                "       FROM product " +
                "       WHERE product.deleted_at IS NULL" +
                "       LIMIT ? OFFSET ?";
        List<Product> results = jdbcTemplate.query(query, productMapper, new Object[] {request.getSize(), request.getPage()});
        return results;
    }

    @Override
    public List<Product> getWishList(Long userId, ProductRequest request) {
        String query = "SELECT product.id, sku, product.name, price, quantity, view, product.created_at, updated_at" +
                "       FROM product " +
                "       INNER JOIN wish ON wish.product_id = product.id" +
                "       INNER JOIN users ON users.id = ? " +
                "       WHERE product.deleted_at IS NULL" +
                "       LIMIT ? OFFSET ?";
        List<Product> results = jdbcTemplate.query(query, productMapper, new Object[] {userId, request.getSize(), request.getPage()});
        return results;
    }

    @Override
    public List<Product> getNotWishProductList(Long userId, ProductRequest request) {
        String query = "SELECT product.id, sku, product.name, price, quantity, view, product.created_at, updated_at" +
                "       FROM product " +
                "       WHERE product.deleted_at IS NULL AND product.id NOT IN " +
                "       (SELECT product.id" +
                "        FROM product" +
                "        INNER JOIN wish ON wish.product_id = product.id" +
                "        INNER JOIN users ON users.id = ? " +
                "        WHERE product.deleted_at IS NULL" +
                "       )" +
                "       LIMIT ? OFFSET ?";
        List<Product> results = jdbcTemplate.query(query, productMapper, new Object[] {userId, request.getSize(), request.getPage()});
        return results;
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
                    .updatedAt(rs.getTimestamp("updated_at").toLocalDateTime())
                    .build();


}
